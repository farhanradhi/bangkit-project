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
    
    # Ambil alamat dan lokasi (lat, lng) dari input JSON
    user_address = data.get('address')
    user_lat = data.get('lat')
    user_lng = data.get('lng')
    radius = data.get('radius', 5000)  # Default radius 5000 meter jika tidak diberikan
    
    # Tentukan lokasi berdasarkan input
    if user_address:
        # Jika ada alamat, lakukan geocoding
        geocode_result = gmaps.geocode(user_address)
        if not geocode_result:
            return jsonify({"error": "Alamat tidak valid."}), 400
        location = geocode_result[0]['geometry']['location']
        user_lat = location['lat']
        user_lng = location['lng']
    
    if not user_lat or not user_lng:
        return jsonify({"error": "Lokasi tidak ditemukan."}), 400

    # Daftar kata kunci untuk pencarian tempat yang relevan
    keywords = ['waste disposal', 'recycling center', 'garbage dump', 'waste management', 'landfill']
    
    # Array untuk menampung hasil pencarian
    results = []
    
    for keyword in keywords:
        # Pencarian tempat berdasarkan kata kunci dan radius
        places = gmaps.places_nearby(
            location=(user_lat, user_lng),
            radius=radius,
            keyword=keyword,
            type='point_of_interest'  # Menyaring hasil berdasarkan kategori point of interest
        )
        
        for place in places.get('results', []):
            results.append({
                "name": place['name'],
                "address": place.get('vicinity', 'No address'),
                "location": place['geometry']['location']
            })
    
    # Hapus duplikasi tempat berdasarkan kombinasi nama dan alamat
    unique_results = []
    seen_places = set()
    for result in results:
        place_id = result['name'] + result['address']
        if place_id not in seen_places:
            seen_places.add(place_id)
            unique_results.append(result)
    
    # Return hasil dalam format yang diinginkan
    return jsonify({
        "MapsResponses": unique_results
    })

# Main Function untuk Cloud Run
if __name__ == '__main__':
    port = int(os.environ.get("PORT", 8080))  # Default ke 8080 jika PORT tidak disetel
    app.run(host='0.0.0.0', port=port)  # Dengarkan pada semua alamat (host 0.0.0.0)
