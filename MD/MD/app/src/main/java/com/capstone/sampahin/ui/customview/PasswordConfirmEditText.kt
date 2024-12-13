package com.capstone.sampahin.ui.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.sampahin.R

class PasswordConfirmEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private var passwordVisible: Boolean = false
    private var visibilityIcon: Drawable? = null
    private var visibilityOffIcon: Drawable? = null
    private var visibilityLockIcon: Drawable? = null

    init {
        setupPasswordEditText()
    }

    private fun setupPasswordEditText() {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        hint = context.getString(R.string.confirm_password)

        visibilityIcon = ContextCompat.getDrawable(context, R.drawable.baseline_visibility_24)
        visibilityOffIcon = ContextCompat.getDrawable(context, R.drawable.baseline_visibility_off_24)
        visibilityLockIcon = ContextCompat.getDrawable(context, R.drawable.baseline_lock_24)

        setCompoundDrawablesRelativeWithIntrinsicBounds(visibilityLockIcon, null, visibilityOffIcon, null)
    }

    private fun updatePasswordVisibilityIcon() {
        val icon = if (passwordVisible) visibilityIcon else visibilityOffIcon
        setCompoundDrawablesRelativeWithIntrinsicBounds(visibilityLockIcon, null, icon, null)
    }

    private fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
        inputType = if (passwordVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        setSelection(text?.length ?: 0)
        updatePasswordVisibilityIcon()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            val drawableEnd = compoundDrawablesRelative[2]
            if (drawableEnd != null) {
                val drawableStart = width - paddingEnd - drawableEnd.bounds.width()
                if (event.x >= drawableStart) {
                    togglePasswordVisibility()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        if (s.length < 8) {
            setError(context.getString(R.string.password_min_character), null)
        } else {
            error = null
        }
    }
}
