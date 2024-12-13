package com.capstone.sampahin.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.sampahin.R
import com.capstone.sampahin.data.TokenPreferences
import com.capstone.sampahin.databinding.FragmentProfileBinding
import com.capstone.sampahin.ui.login.LoginActivity
import com.capstone.sampahin.ui.login.LoginViewModelFactory
import com.capstone.sampahin.ui.profilemenu.history.HistoryActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenPref : TokenPreferences
    private val viewModel: ProfileViewModel by viewModels {
        LoginViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenPref = TokenPreferences(requireActivity())

        binding.signOutButton.setOnClickListener {
            logout()
        }

        binding.historyButton.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.aboutButton.setOnClickListener {
            showAboutUsDialog()
        }

        binding.contactButton.setOnClickListener {
            showContactDialog()
        }


        binding.settingsButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        lifecycleScope.launch {
            val token = tokenPref.getToken()
            if (token != null) {
                val user = viewModel.getUser(token)
                binding.tvUsername.text = user.name
                binding.tvEmail.text = user.email
            } else {
                Toast.makeText(requireActivity(),
                    getString(R.string.user_not_found), Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun logout() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.sign_out))
        builder.setMessage(getString(R.string.logout_confirmation))

        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            tokenPref.clearToken()
            Toast.makeText(requireActivity(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }


    private fun showAboutUsDialog() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.about_us))
        builder.setMessage(getString(R.string.about_us_description))
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun showContactDialog() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.contact))
        builder.setMessage(getString(R.string.contact_description))
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

}
