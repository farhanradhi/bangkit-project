from flask import Flask, request, jsonify
from tensorflow.keras.preprocessing import image
import numpy as np
import tensorflow as tf
import os
import io
from PIL import Image
from google.cloud import storage
from google.cloud import firestore
from datetime import datetime

app = Flask(__name__)

# Memuat model ML yang telah dilatih
model = tf.keras.models.load_model('./model/sampahio.h5')  # Gantilah dengan path model yang sesuai

# Nama kelas yang telah dilatih
class_names = ['Besi', 'Daun', 'Kaca', 'Kardus', 'Kayu', 'Kertas', 'Plastik', 'Sisa Makanan']
threshold_confidence = 0.5  # Threshold minimum untuk mendeteksi sampah

# Inisialisasi Firestore client
db = firestore.Client()

# Fungsi untuk memproses gambar
def process_image(img):
    img_array = image.img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)
    img_array /= 255.0
    return img_array

# Fungsi untuk meng-upload gambar ke Google Cloud Storage
def upload_to_gcs(image, bucket_name, destination_blob_name):
    client = storage.Client()
    bucket = client.get_bucket(bucket_name)
    img_io = io.BytesIO()
    image.save(img_io, 'PNG')
    img_io.seek(0)
    blob = bucket.blob(destination_blob_name)
    blob.upload_from_file(img_io, content_type='image/png')
    return f"gs://{bucket_name}/{destination_blob_name}"

# Endpoint untuk prediksi
# Endpoint untuk prediksi
@app.route('/predict', methods=['POST'])
def predict():
    if 'file' not in request.files:
        return jsonify({'error': 'No file part'}), 400
    
    file = request.files['file']
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    ALLOWED_EXTENSIONS = {'jpg', 'jpeg', 'png'}
    if not ('.' in file.filename and file.filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS):
        return jsonify({'error': 'File type not allowed. Please upload a .jpg, .jpeg, or .png file.'}), 400

    img = Image.open(io.BytesIO(file.read()))
    img = img.resize((224, 224))
    img_array = process_image(img)
    predictions = model.predict(img_array)
    predicted_class_index = np.argmax(predictions[0])
    confidence = float(predictions[0][predicted_class_index])

    # Jika confidence di bawah threshold, tandai sebagai "Bukan Sampah"
    if confidence < threshold_confidence:
        predicted_class = "Bukan Sampah"
    else:
        predicted_class = class_names[predicted_class_index]

    # Simpan gambar hasil prediksi ke GCS
    bucket_name = 'sampahin_ml_datasets'  # Ganti dengan nama bucket kamu
    destination_blob_name = f'predicted_images/{file.filename}'
    image_url = upload_to_gcs(img, bucket_name, destination_blob_name)

    # Buat ID unik untuk Firestore
    prediction_ref = db.collection('predictions').document()  # Generate ID unik
    prediction_id = prediction_ref.id  # Ambil ID yang dihasilkan

    # Data hasil prediksi
    result = {
        'id': prediction_id,  # Tambahkan ID ke data
        'predicted_class': predicted_class,
        'confidence': confidence,
        'image_url': image_url,
        'timestamp': datetime.utcnow()
    }

    # Simpan hasil ke Firestore
    prediction_ref.set(result)  # Menggunakan set() untuk menyimpan data

    return jsonify({
        'id': prediction_id,  # Kembalikan ID ke response
        'predicted_class': result['predicted_class'],
        'confidence': result['confidence'],
        'image_url': result['image_url'],
        'timestamp': result['timestamp'].isoformat()
    })


# Endpoint untuk mengambil histori prediksi
@app.route('/history', methods=['GET'])
def get_history():
    try:
        predictions_ref = db.collection('predictions')
        docs = predictions_ref.stream()

        history = []
        for doc in docs:
            data = doc.to_dict()
            data['id'] = doc.id
            history.append(data)

        return jsonify({'history': history}), 200

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=5000)




