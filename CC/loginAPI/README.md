# Firebase Authentication API
API ini menyediakan fitur registrasi, login dengan email/password, dan login menggunakan akun Google. Backend dibangun menggunakan Flask, dengan Firebase sebagai layanan autentikasi dan database.

Base URL

Cloud Run: https://<CLOUD_RUN_URL>

Local: http://127.0.0.1:8080

## 1. Register User
Endpoint
POST /register

Deskripsi
Mendaftarkan akun baru menggunakan email dan password.

Request Body
json
Copy code
{
  "email": "user@example.com",
  "password": "securepassword"
}
Response
201 Created
json
Copy code
{
  "message": "Registrasi berhasil.",
  "token": "<JWT_TOKEN>"
}
400 Bad Request
json
Copy code
{
  "error": "Email sudah digunakan."
}
500 Internal Server Error
json
Copy code
{
  "error": "Deskripsi error."
}
2. Login User
Endpoint
POST /login

Deskripsi
Login menggunakan email dan password.

Request Body
json
Copy code
{
  "email": "user@example.com",
  "password": "securepassword"
}
Response
200 OK
json
Copy code
{
  "message": "Login berhasil.",
  "token": "<JWT_TOKEN>"
}
404 Not Found
json
Copy code
{
  "error": "Email tidak ditemukan."
}
401 Unauthorized
json
Copy code
{
  "error": "Password salah."
}
500 Internal Server Error
json
Copy code
{
  "error": "Deskripsi error."
}
