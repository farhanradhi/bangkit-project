package com.capstone.sampahin.ui.profilemenu.history

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sampahin.R
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyViewModel: HistoryViewModel by viewModels{
            HistoryViewModelFactory.getInstance()
        }

        historyViewModel.getHistory().observe(this) { result ->
            when (result) {
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    Log.d("HistoryActivity", "Error: ${result.error}")
                }
                is Result.Loading -> {
                    showLoading(true)
                    Log.d("HistoryActivity", "Loading data...")
                }
                is Result.Success -> {
                    showLoading(false)
                    Log.d("HistoryActivity", "Data received: ${result.data}")
                    historyAdapter.submitList(result.data)
                }
            }
        }


        historyAdapter = HistoryAdapter()
        binding.rvHistory.adapter = historyAdapter
        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        Log.d("HistoryActivity", "historyAdapter: $historyAdapter")

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}