from flask import Flask, request, jsonify
from tensorflow.keras.preprocessing import image
import numpy as np
import tensorflow as tf
import os
import io
from PIL import Image
from google.cloud import storage

app = Flask(__name__)

# Memuat model ML yang telah dilatih
model = tf.keras.models.load_model('./model/sampahio.h5')  # Gantilah dengan path model yang sesuai

# Nama kelas yang telah dilatih
class_names = ['Besi', 'Daun', 'Kaca', 'Kardus', 'Kayu', 'Kertas', 'Plastik', 'Sisa Makanan']

# Fungsi untuk memproses gambar
def process_image(img):
    # Mengubah gambar menjadi array dan melakukan normalisasi
    img_array = image.img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)
    img_array /= 255.0
    return img_array

# Fungsi untuk meng-upload gambar ke Google Cloud Storage
def upload_to_gcs(image, bucket_name, destination_blob_name):
    # Inisialisasi client GCS
    client = storage.Client()

    # Akses bucket
    bucket = client.get_bucket(bucket_name)

    # Simpan gambar ke dalam buffer
    img_io = io.BytesIO()
    image.save(img_io, 'PNG')  # Bisa disesuaikan formatnya, misalnya 'JPEG', 'PNG'
    img_io.seek(0)

    # Tentukan blob GCS
    blob = bucket.blob(destination_blob_name)

    # Upload gambar ke GCS
    blob.upload_from_file(img_io, content_type='image/png')
    
    return f"gs://{bucket_name}/{destination_blob_name}"

# Endpoint untuk prediksi
@app.route('/predict', methods=['POST'])
def predict():
    if 'file' not in request.files:
        return jsonify({'error': 'No file part'}), 400
    
    file = request.files['file']
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400
    
    # Membaca file gambar yang diupload
    img = Image.open(io.BytesIO(file.read()))  # Membaca gambar dari file yang diupload
    
    # Mengubah ukuran gambar sesuai target size
    img = img.resize((224, 224))
    
    # Memproses gambar
    img_array = process_image(img)
    
    # Melakukan prediksi
    predictions = model.predict(img_array)
    predicted_class = np.argmax(predictions[0])
    
    # Menyimpan gambar hasil prediksi ke GCS
    result_image = Image.fromarray((img_array[0] * 255).astype(np.uint8))  # Ubah kembali ke gambar
    bucket_name = ''  # Gantilah dengan nama bucket kamu
    destination_blob_name = f'predicted_images/{file.filename}'  # Tentukan nama file di GCS

    image_url = upload_to_gcs(result_image, bucket_name, destination_blob_name)
    
    # Mengembalikan hasil prediksi dan URL gambar yang di-upload ke GCS
    result = {
        'predicted_class': class_names[predicted_class],
        'confidence': float(predictions[0][predicted_class]),
        'image_url': image_url
    }
    
    return jsonify(result)

if __name__ == '__main__':
    app.run(debug=True, port=5000)
