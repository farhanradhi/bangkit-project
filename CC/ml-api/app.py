from flask import Flask, request, jsonify, render_template
import numpy as np
import tensorflow as tf
import joblib
from tensorflow.keras.preprocessing import image
import os
from werkzeug.utils import secure_filename
import matplotlib.pyplot as plt
from io import BytesIO
import base64

app = Flask(__name__)

# Load the pre-trained model
model = joblib.load('model_inceptionv3.joblib')

# Class names (sesuaikan dengan label yang digunakan saat training)
class_names = ['Besi', 'Kaca', 'Kardus', 'Kertas', 'Plastik', 'Sisa Makanan']

# Define allowed file extensions for image upload
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

# Prediction function
def predict_and_display(model, img_path, target_size, class_names):
    img = image.load_img(img_path, target_size=target_size)
    img_array = image.img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)  # Add batch dimension
    img_array /= 255.0  # Normalize
    
    predictions = model.predict(img_array)
    predicted_class = np.argmax(predictions[0])
    
    return class_names[predicted_class], predictions[0][predicted_class]

# Route for index page
@app.route('/')
def index():
    return render_template('index.html')

# Route for predicting the image
@app.route('/predict', methods=['POST'])
def predict():
    # Check if the file part is present in the request
    if 'file' not in request.files:
        return jsonify({'error': 'No file part'}), 400
    
    file = request.files['file']
    
    # If no file is selected, return an error
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400
    
    # If file is allowed, save and make prediction
    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        file_path = os.path.join('static', filename)
        file.save(file_path)
        
        # Make prediction
        predicted_class, confidence = predict_and_display(model, file_path, (224, 224), class_names)
        
        # Return result as JSON response
        return jsonify({
            'predicted_class': predicted_class,
            'confidence': confidence
        })

    return jsonify({'error': 'File type not allowed'}), 400

# Run the Flask app
if __name__ == '__main__':
    app.run(debug=True)
