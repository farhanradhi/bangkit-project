from flask import Flask, request, jsonify
from config import download_csv_from_gcs
import logging
logging.basicConfig(level=logging.DEBUG)

app = Flask(__name__)

# Unduh data CSV saat server dimulai
from config import download_csv_from_gcs
import pandas as pd

# Parameter GCS
BUCKET_NAME = "sampahin_ml_datasets"
SOURCE_BLOB_NAME = "chatbot-dataset/toptwenty.csv"

# Unduh file CSV dari GCS
local_file_path = download_csv_from_gcs(BUCKET_NAME, SOURCE_BLOB_NAME)

# Baca file CSV menggunakan pandas
data = pd.read_csv(local_file_path)
print("Data berhasil dimuat:")
print(data.head())


# Struktur data
def process_data(df):
    processed_data = {
        "topics": [],
        "questions": {},
        "answers": {}
    }
    current_category = None

    for _, row in df.iterrows():
        category = row["Kategori"]
        if category not in processed_data["topics"]:
            processed_data["topics"].append(category)
            processed_data["questions"][category] = []
            processed_data["answers"][category] = []

        processed_data["questions"][category].append(row["Pertanyaan"])
        processed_data["answers"][category].append(row["Jawaban"])
    return processed_data

# Load data ke dalam struktur dictionary
data_dict = process_data(data)

# Endpoint untuk mendapatkan daftar topik
@app.route("/topics", methods=["GET"])
def get_topics():
    return jsonify({"topics": data_dict["topics"]})

# Endpoint untuk mendapatkan pertanyaan berdasarkan topik
@app.route("/questions/<string:topic>", methods=["GET"])
def get_questions(topic):
    if topic in data_dict["questions"]:
        return jsonify({"questions": data_dict["questions"][topic]})
    return jsonify({"error": "Topik tidak ditemukan"}), 404

# Endpoint untuk mendapatkan jawaban berdasarkan pertanyaan
@app.route("/answer", methods=["POST"])
def get_answer():
    request_data = request.get_json()
    topic = request_data.get("topic")
    question = request_data.get("question")
    
    response_answers = []  # List to store matching answers

    # Check if question is empty
    if not question:
        return jsonify({"error": "pertanyaan tidak boleh kosong!"}), 400

    # Check if topic exists in data_dict
    if topic in data_dict["questions"]:
        for idx, q in enumerate(data_dict["questions"][topic]):
            if question.lower() in q.lower():
                response_answers.append(data_dict["answers"][topic][idx])
        
        # Check if there are matching answers
        if response_answers:
            return jsonify({"answers": response_answers})
        else:
            return jsonify({"error": "maaf pertanyaan tidak ditemukan"}), 404

    # Return error if topic is not found
    return jsonify({"error": "Topik tidak ditemukan"}), 404
# Endpoint default
@app.route("/", methods=["GET"])
def home():
    return jsonify({"message": "Selamat datang di Rule-Based Chatbot API!"})

if __name__ == "__main__":
    app.run(debug=True, port=5050)
