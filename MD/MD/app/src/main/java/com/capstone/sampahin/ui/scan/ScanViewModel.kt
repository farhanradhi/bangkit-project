package com.capstone.sampahin.ui.scan

import androidx.lifecycle.ViewModel
import android.net.Uri

class ScanViewModel : ViewModel() {
    var currentImageUri: Uri? = null
    var resultLabel: String? = null
    var description: String? = null
    var result: String? = null

    var isResultVisible: Boolean = false
    var isDescriptionVisible: Boolean = false
    var isChatButtonVisible: Boolean = false
}
