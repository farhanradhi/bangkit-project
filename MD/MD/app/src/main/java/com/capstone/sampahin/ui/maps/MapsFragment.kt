package com.capstone.sampahin.ui.maps

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.capstone.sampahin.R
import com.capstone.sampahin.databinding.FragmentMapsBinding
import com.capstone.sampahin.data.Result
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()
    private lateinit var binding: FragmentMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val mapsAdapter: MapsAdapter by lazy { MapsAdapter() }

    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModelFactory.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMapsBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        recycleView()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isIndoorLevelPickerEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMapToolbarEnabled = true

        val initialLocation = LatLng(-6.200000, 106.816666)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 15f))

        binding.myLocationButton.setOnClickListener {
            getMyLocation()
        }

        observeMap()
        initMap()

    }

    private fun recycleView() {
        val layout = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMaps.adapter = mapsAdapter
        binding.rvMaps.layoutManager = layout
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLocation()
                }

                permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLocation()
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.location_permission_denied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun observeMap() {
        mapsViewModel.mapsResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val places = result.data.mapsResponses
                    places?.forEach { place ->
                        place?.location?.let {
                            val latLng = LatLng(it.lat as Double, it.lng as Double)
                            val markerIcon = resizeDrawableToBitmap(R.drawable.location_icons, 40)
                            map.addMarker(
                                MarkerOptions().position(latLng).title(place.name)
                                    .snippet(place.address).icon(
                                        markerIcon
                                    )
                            )
                            boundsBuilder.include(latLng)
                        }
                        binding.placeholderLayout.visibility = View.GONE
                    }

                    if (result.data.mapsResponses.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.maps_fail),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val bounds = boundsBuilder.build()
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds,
                                resources.displayMetrics.widthPixels,
                                resources.displayMetrics.heightPixels,
                                300
                            )
                        )
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds,
                                resources.displayMetrics.widthPixels,
                                resources.displayMetrics.heightPixels,
                                300
                            )
                        )
                    }
                    mapsAdapter.submitList(places)
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Error:", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initMap() {
        binding.addAddressButton.setOnClickListener {
            val address = binding.etAddress.text.toString()
            val radius = binding.etRadius.text.toString().toIntOrNull()
            var isValid = true

            if (address.isEmpty()) {
                binding.etlAddress.error = getString(R.string.must_fill)
                isValid = false
            } else {
                binding.etlAddress.error = null
            }

            if (radius != null && radius > 0) {
                binding.etlRadius.error = null
            } else {
                binding.etlRadius.error = getString(R.string.must_fill)
                isValid = false
            }

            if (isValid) {
                hideKeyboard()
                mapsViewModel.fetchMapsData(address, radius!!)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.must_fill_address),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun getMyLocation() {
        if (checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val lat = location.latitude
                        val lng = location.longitude
                        val radius = binding.etRadiusMyLocation.text.toString().toIntOrNull()

                        if (radius != null) {
                            hideKeyboard()
                            val response = "$lat,$lng"
                            mapsViewModel.fetchMapsData(response, radius)
                            Log.d(TAG, "Lat: $lat, Lng: $lng")
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.location_success), Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            binding.etlRadiusMyLocation.error = getString(R.string.must_fill_radius)
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.failed_to_get_location), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                    Log.e(TAG, "Error getting location: ${e.message}")
                }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun resizeDrawableToBitmap(drawableRes: Int, dpSize: Int): BitmapDescriptor {
        val drawable = ContextCompat.getDrawable(requireContext(), drawableRes)
            ?: return BitmapDescriptorFactory.defaultMarker()
        val width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpSize.toFloat(),
            resources.displayMetrics
        ).toInt()

        val height = width * 2
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        val view = requireActivity().currentFocus
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    companion object {
        private const val TAG = "MapsActivity"
    }
}
