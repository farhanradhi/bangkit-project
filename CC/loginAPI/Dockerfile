# Gunakan base image Python
FROM python:3.9-slim

# Set working directory
WORKDIR /app

# Copy semua file ke container
COPY . /app

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Expose port 8080 (default Cloud Run)
EXPOSE 8080

# Jalankan aplikasi
CMD ["python", "app.py"]
