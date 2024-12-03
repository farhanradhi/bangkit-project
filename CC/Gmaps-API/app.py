import os
import googlemaps
from flask import Flask, request, jsonify

app = Flask(__name__)

# Ambil API Key dari environment variable
gmaps = googlemaps.Client(key=os.getenv('GOOGLE_MAPS_API_KEY'))

@app.route('/find_places', methods=['GET'])
def find_places():
    user_lat = request.args.get('lat')
    user_lng = request.args.get('lng')
    radius = 5000  # dalam meter, sesuaikan dengan kebutuhan

    if not user_lat or not user_lng:
        return jsonify({"error": "Parameter 'lat' dan 'lng' diperlukan."}), 400

    # Query untuk mencari tempat terdekat berdasarkan kata kunci
    try:
        places = gmaps.places_nearby(
            location=(float(user_lat), float(user_lng)),
            radius=radius,
            keyword='waste disposal'
        )
    except Exception as e:
        return jsonify({"error": str(e)}), 500

    results = []
    for place in places.get('results', []):
        results.append({
            'name': place['name'],
            'address': place.get('vicinity', 'No address'),
            'location': place['geometry']['location']
        })
    
    return jsonify(results)

# Main Function untuk Cloud Run
if __name__ == '__main__':
    port = int(os.environ.get("PORT", 8080))  # Default ke 8080 jika PORT tidak disetel
    app.run(host='0.0.0.0', port=port)  # Dengarkan pada semua alamat (host 0.0.0.0)
