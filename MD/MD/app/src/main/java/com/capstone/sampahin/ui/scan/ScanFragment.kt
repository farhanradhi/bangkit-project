package com.capstone.sampahin.ui.scan

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.capstone.sampahin.R
import com.capstone.sampahin.data.api.ApiConfig
import com.capstone.sampahin.databinding.FragmentScanBinding
import com.capstone.sampahin.ui.reduceFileImage
import com.capstone.sampahin.ui.scan.CameraActivity.Companion.CAMERAX_RESULT
import com.capstone.sampahin.ui.uriToFile
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireActivity(), "Permission Request granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireActivity(), "Permission Request Denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(
            requireActivity(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { uploadImage() }
        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.chatButton.setOnClickListener { moveToChat() }

        Glide.with(this)
            .asGif()
            .load(R.drawable.recycle_placeholder)
            .into(binding.previewImageView)

        updateUI()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog()
            }
        })
    }


    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCameraX() {
        val intent = Intent(requireActivity(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            viewModel.currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()

            viewModel.currentImageUri?.let { uri ->
                startCrop(uri)
            }
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            startCrop(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        viewModel.currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun startCrop(uri: Uri) {
        val time = System.currentTimeMillis()
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_image_$time.jpg"))
        cropImageLauncher.launch(UCrop.of(uri, destinationUri).getIntent(requireContext()))
    }

    private val cropImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultUri = UCrop.getOutput(result.data!!)
            resultUri?.let { uri ->
                viewModel.currentImageUri = uri
                showImage()
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(result.data!!)
            cropError?.let { error ->
                error.printStackTrace()
                showToast("Cropping failed: ${error.message}")
            }
        }
    }

    private fun uploadImage() {
        viewModel.currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            showLoading(true)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getMLApiService()
                    val response = apiService.predict(multipartBody)

                    val translatedLabel = translateLabel(response.predictedClass!!)
                    val description = getDescription(response.predictedClass)
                    val result = String.format(
                        getString(R.string.result_label),
                        translatedLabel,
                        (response.confidence as Double) * 100
                    )

                    viewModel.resultLabel = result
                    viewModel.description = description

                    viewModel.resultLabel = result
                    viewModel.description = description
                    viewModel.result = getString(R.string.result)
                    viewModel.isResultVisible = true
                    viewModel.isDescriptionVisible = true
                    viewModel.isChatButtonVisible = true

                    updateUI()

                    binding.root.post {
                        val targetY = binding.Result.top
                        binding.scrollView.scrollTo(0, targetY)
                    }

                    showToast("Predicted class: $translatedLabel")
                    showLoading(false)
                } catch (e: HttpException) {
                    val errorMessage = e.response()?.errorBody()?.string() ?: "HTTP error occurred"
                    showToast(errorMessage)
                    showLoading(false)
                } catch (e: Exception) {
                    showToast(e.message ?: "An unexpected error occurred")
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun moveToChat() {
        val action = ScanFragmentDirections.actionNavigationScanToNavigationTopics()
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(action)
    }



    private fun translateLabel(label: String): String {
        return when (label.lowercase()) {
            "besi" -> getString(R.string.besi)
            "daun" -> getString(R.string.daun)
            "kaca" -> getString(R.string.kaca)
            "kardus" -> getString(R.string.kardus)
            "kayu" -> getString(R.string.kayu)
            "kertas" -> getString(R.string.kertas)
            "plastik" -> getString(R.string.plastik)
            "sisa makanan" -> getString(R.string.sisa_makanan)
            "bukan sampah" -> getString(R.string.bukan_sampah)
            else -> label
        }
    }

    private fun getDescription(label: String): String {
        return when (label.lowercase()) {
            "besi" -> getString(R.string.desc_besi)
            "daun" -> getString(R.string.desc_daun)
            "kaca" -> getString(R.string.desc_kaca)
            "kardus" -> getString(R.string.desc_kardus)
            "kayu" -> getString(R.string.desc_kayu)
            "kertas" -> getString(R.string.desc_kertas)
            "plastik" -> getString(R.string.desc_plastik)
            "sisa makanan" -> getString(R.string.desc_sisa_makanan)
            "bukan sampah" -> getString(R.string.desc_bukan_sampah)
            else -> ""
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.scan_fragment))
            .setMessage(getString(R.string.confirm_exit_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }



    private fun updateUI() {
        binding.tvResult.text = viewModel.resultLabel ?: ""
        binding.tvResult.visibility = if (viewModel.isResultVisible) View.VISIBLE else View.GONE

        binding.descResult.text = viewModel.description ?: ""
        binding.descResult.visibility = if (viewModel.isDescriptionVisible) View.VISIBLE else View.GONE

        binding.Result.text = viewModel.result ?: ""
        binding.Result.visibility = if (viewModel.isResultVisible) View.VISIBLE else View.GONE

        binding.chatButton.visibility = if (viewModel.isChatButtonVisible) View.VISIBLE else View.GONE

        viewModel.currentImageUri?.let { uri ->
            binding.previewImageView.setImageURI(uri)
        }
    }

}