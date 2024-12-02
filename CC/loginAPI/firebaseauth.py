from flask import Flask, request, jsonify
import firebase_admin
from firebase_admin import credentials, auth, firestore
from werkzeug.security import generate_password_hash, check_password_hash
import jwt
import datetime
import os

# Inisialisasi Flask
app = Flask(__name__)

# Inisialisasi Firebase
cred = credentials.Certificate("#####") #Path ke Credentials untuk Firebase Authentication
firebase_admin.initialize_app(cred)
db = firestore.client()

# Secret Key untuk JWT
SECRET_KEY = "ur_sercet_keys"  # Ganti dengan key aman


# Helper Function: Hashing dan Verifikasi Password
def hash_password(password):
    return generate_password_hash(password)


def verify_password(hashed_password, plain_password):
    return check_password_hash(hashed_password, plain_password)


# API Register
@app.route('/register', methods=['POST'])
def register():
    try:
        data = request.json
        email = data['email']
        password = data['password']

        # Cek apakah email sudah digunakan
        user_query = db.collection('users').where('email', '==', email).get()
        if user_query:
            return jsonify({'error': 'Email sudah digunakan.'}), 400

        # Hash password sebelum disimpan
        hashed_password = hash_password(password)

        # Simpan user ke Firestore
        user_ref = db.collection('users').document()
        user_ref.set({
            'email': email,
            'password': hashed_password,
            'created_at': datetime.datetime.now()
        })

        # Buat JWT token
        token = jwt.encode({'email': email, 'exp': datetime.datetime.utcnow() + datetime.timedelta(hours=24)}, SECRET_KEY)

        return jsonify({'message': 'Registrasi berhasil.', 'token': token}), 201
    except Exception as e:
        return jsonify({'error': str(e)}), 500


# API Login
@app.route('/login', methods=['POST'])
def login():
    try:
        data = request.json
        email = data['email']
        password = data['password']

        # Ambil user berdasarkan email
        user_query = db.collection('users').where('email', '==', email).get()
        if not user_query:
            return jsonify({'error': 'Email tidak ditemukan.'}), 404

        # Ambil data user
        user_data = user_query[0].to_dict()

        # Verifikasi password
        if not verify_password(user_data['password'], password):
            return jsonify({'error': 'Password salah.'}), 401

        # Buat JWT token
        token = jwt.encode({'email': email, 'exp': datetime.datetime.utcnow() + datetime.timedelta(hours=24)}, SECRET_KEY)

        return jsonify({'message': 'Login berhasil.', 'token': token}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


# API Login Google
@app.route('/google-login', methods=['POST'])
def google_login():
    try:
        data = request.json
        id_token = data['id_token']

        # Verifikasi Google ID Token
        decoded_token = auth.verify_id_token(id_token)
        email = decoded_token['email']

        # Periksa apakah user sudah terdaftar
        user_query = db.collection('users').where('email', '==', email).get()
        if not user_query:
            # Jika belum, daftar user baru
            user_ref = db.collection('users').document()
            user_ref.set({
                'email': email,
                'created_at': datetime.datetime.now()
            })

        # Buat JWT token
        token = jwt.encode({'email': email, 'exp': datetime.datetime.utcnow() + datetime.timedelta(hours=24)}, SECRET_KEY)

        return jsonify({'message': 'Login Google berhasil.', 'token': token}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


# Main Function untuk Cloud Run
if __name__ == '__main__':
    port = int(os.environ.get("PORT", 8080))  # Default ke 8080 jika PORT tidak disetel
    app.run(host='0.0.0.0', port=port)  # Dengarkan pada semua alamat (host 0.0.0.0)
