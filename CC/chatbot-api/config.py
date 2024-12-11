from google.cloud import storage
import os

def download_csv_from_gcs(bucket_name, source_blob_name):
    # Direktori /tmp adalah writable di App Engine
    local_file_path = f"/tmp/{source_blob_name.split('/')[-1]}"
    
    # Inisialisasi client
    storage_client = storage.Client()
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(source_blob_name)

    # Unduh file ke /tmp
    blob.download_to_filename(local_file_path)
    print(f"File {source_blob_name} berhasil diunduh ke {local_file_path}")
    return local_file_path

