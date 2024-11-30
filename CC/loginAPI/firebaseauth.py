import requests
from firebase_admin import credentials, initialize_app, firestore
from flask import Flask, request, jsonify

# Inisialisasi Firebase Admin SDK
cred = credentials.Certificate("firebase_auth.json")  # Path ke file JSON Firebase Admin SDK
initialize_app(cred)

# Firestore database
db = firestore.client()

app = Flask(__name__)

# Ganti dengan API key Firebase Anda
FIREBASE_API_KEY = "firebase_auth.json"

@app.route('/register-email', methods=['POST'])
def register_email():
    """
    Endpoint untuk register pengguna menggunakan email dan password.
    """
    try:
        data = request.get_json()
        email = data['email']
        password = data['password']
        name = data.get('name', 'No name provided')

        # Endpoint Firebase REST API untuk register
        url = f"https://identitytoolkit.googleapis.com/v1/accounts:signUp?key={FIREBASE_API_KEY}"
        payload = {
            "email": email,
            "password": password,
            "returnSecureToken": True
        }
        response = requests.post(url, json=payload)

        if response.status_code == 200:
            user_data = response.json()
            uid = user_data['localId']

            # Simpan data pengguna di Firestore
            user_ref = db.collection('users').document(uid)
            user_ref.set({
                'uid': uid,
                'email': email,
                'name': name
            })

            return jsonify({"message": "User registered successfully", "user": user_data}), 201
        else:
            return jsonify({"error": response.json()}), response.status_code

    except Exception as e:
        return jsonify({"error": str(e)}), 400


@app.route('/login-email', methods=['POST'])
def login_email():
    """
    Endpoint untuk login pengguna menggunakan email dan password.
    """
    try:
        data = request.get_json()
        email = data['email']
        password = data['password']

        # Endpoint Firebase REST API untuk login
        url = f"https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={FIREBASE_API_KEY}"
        payload = {
            "email": email,
            "password": password,
            "returnSecureToken": True
        }
        response = requests.post(url, json=payload)

        if response.status_code == 200:
            user_data = response.json()
            uid = user_data['localId']

            # Ambil data pengguna dari Firestore
            user_ref = db.collection('users').document(uid)
            user_doc = user_ref.get()

            if user_doc.exists():
                return jsonify({"message": "User logged in successfully", "user": user_doc.to_dict()}), 200
            else:
                return jsonify({"message": "User does not exist in Firestore"}), 404
        else:
            # Jika login gagal (email/password salah)
            error_message = response.json().get('error', {}).get('message', 'Email atau Password yang dimasukan salah')
            return jsonify({"error": f"Login failed: {error_message}"}), 401

    except Exception as e:
        return jsonify({"error": str(e)}), 400


@app.route('/login-google', methods=['POST'])
def login_google():
    """
    Endpoint untuk login/register pengguna menggunakan Google ID token.
    """
    try:
        data = request.get_json()
        id_token = data['id_token']

        # Verifikasi ID token dengan Firebase Admin SDK
        decoded_token = auth.verify_id_token(id_token)
        uid = decoded_token['uid']

        # Ambil data pengguna dari Firestore
        user_ref = db.collection('users').document(uid)
        user = user_ref.get()

        if user.exists:
            return jsonify({"message": "User logged in successfully", "user": user.to_dict()}), 200
        else:
            # Jika pengguna baru, daftar mereka
            user_ref.set({
                'uid': uid,
                'email': decoded_token['email'],
                'name': decoded_token.get('name', 'No name provided'),
            })
            return jsonify({"message": "User registered successfully", "user": decoded_token}), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 400


if __name__ == '__main__':
    app.run(debug=True)

