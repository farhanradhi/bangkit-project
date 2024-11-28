import firebase_admin
from firebase_admin import credentials, auth, firestore
from flask import Flask, request, jsonify

# Inisialisasi aplikasi Firebase Admin SDK
cred = credentials.Certificate('firebase_config.json')  # Path ke file JSON Firebase Admin SDK
firebase_admin.initialize_app(cred)

app = Flask(__name__)

# Firestore database
db = firestore.client()

@app.route('/login-google', methods=['POST'])
def login_google():
    try:
        # Ambil ID token dari request
        data = request.get_json()
        id_token = data['id_token']
        
        # Verifikasi ID token dengan Firebase
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

@app.route('/register-email', methods=['POST'])
def register_email():
    try:
        data = request.get_json()
        email = data['email']
        password = data['password']

        # Membuat akun baru dengan email dan password
        user = auth.create_user(
            email=email,
            password=password
        )

        # Simpan data pengguna di Firestore
        user_ref = db.collection('users').document(user.uid)
        user_ref.set({
            'uid': user.uid,
            'email': email,
            'name': data.get('name', 'No name provided'),
        })

        return jsonify({"message": "User registered successfully", "user": user.uid}), 201

    except Exception as e:
        return jsonify({"error": str(e)}), 400

@app.route('/login-email', methods=['POST'])
def login_email():
    try:
        data = request.get_json()
        email = data['email']
        password = data['password']

        # Verifikasi email dan password menggunakan Firebase Authentication
        user = auth.get_user_by_email(email)
        
        # Periksa apakah password yang diberikan valid
        # Jika berhasil, ambil UID pengguna
        uid = user.uid
        
        # Ambil data pengguna dari Firestore
        user_ref = db.collection('users').document(uid)
        user_data = user_ref.get()

        if user_data.exists:
            return jsonify({"message": "User logged in successfully", "user": user_data.to_dict()}), 200
        else:
            return jsonify({"message": "User does not exist in Firestore"}), 404

    except Exception as e:
        return jsonify({"error": str(e)}), 400

@app.route('/register-google', methods=['POST'])
def register_google():
    # Fungsi ini diintegrasikan di route login, jadi tidak perlu terpisah
    return login_google()  # Fungsi login_google sudah menangani login dan register

if __name__ == '__main__':
    app.run(debug=True)
