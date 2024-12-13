package com.capstone.sampahin.ui.customview

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.sampahin.R

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        setupEmailEditText()
    }

    private fun setupEmailEditText() {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        hint = context.getString(R.string.email)

        val emailIcon = ContextCompat.getDrawable(context, R.drawable.baseline_email_24)
        setCompoundDrawablesRelativeWithIntrinsicBounds(emailIcon, null, null, null)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            setError(context.getString(R.string.email_format), null)
        } else {
            error = null
        }
    }
}