import os
import googlemaps
from flask import Flask, request, jsonify

app = Flask(__name__)

# Ambil API Key dari environment variable
gmaps = googlemaps.Client(key=os.getenv('GOOGLE_MAPS_API_KEY'))

@app.route('/find_places', methods=['POST'])
def find_places():
    # Ambil data JSON dari request body
    data = request.get_json()
    
    user_address = data.get('address')  # Dapatkan alamat dari input JSON
    user_lat = data.get('lat')          # Dapatkan latitude dari input JSON
    user_lng = data.get('lng')          # Dapatkan longitude dari input JSON
    radius = data.get('radius', 5000)   # Radius default 5000 meter jika tidak diberikan
    
    if not user_address and (not user_lat or not user_lng):
        return jsonify({"error": "Alamat atau koordinat (lat, lng) harus diberikan."}), 400

    # Menggunakan Geocoding untuk mengubah alamat menjadi koordinat (latitude dan longitude)
    if user_address:
        geocode_result = gmaps.geocode(user_address)
        if not geocode_result:
            return jsonify({"error": "Alamat tidak valid."}), 400
        location = geocode_result[0]['geometry']['location']
        user_lat = location['lat']
        user_lng = location['lng']

    # Kata kunci yang lebih relevan untuk tempat pembuangan sampah atau pengolahan sampah
    keywords = ['waste disposal', 'recycling center', 'garbage dump', 'waste management', 'landfill']

    # Membuat list hasil pencarian
    results = []
    
    for keyword in keywords:
        # Pencarian tempat berdasarkan kata kunci
        places = gmaps.places_nearby(
            location=(user_lat, user_lng),
            radius=radius,
            keyword=keyword,
            type='point_of_interest'  # Menyaring hasil berdasarkan kategori point of interest
        )

        for place in places.get('results', []):
            # Menambahkan hasil yang relevan ke dalam list, hanya menampilkan nama dan lokasi
            results.append({
                'address': place.get('vicinity', 'No address'),  # Alamat tempat
                'location': place['geometry']['location'],  # Lokasi dalam lat, lng
                'name': place['name'],  # Nama tempat
            })
    
    # Menghapus duplikasi tempat jika ada
    unique_results = []
    seen_places = set()
    
    for result in results:
        place_id = result['name'] + str(result['location'])
        if place_id not in seen_places:
            seen_places.add(place_id)
            unique_results.append(result)

    return jsonify(unique_results)

# Main Function untuk Cloud Run
if __name__ == '__main__':
    port = int(os.environ.get("PORT", 8080))  # Default ke 8080 jika PORT tidak disetel
    app.run(host='0.0.0.0', port=port)  # Dengarkan pada semua alamat (host 0.0.0.0)
