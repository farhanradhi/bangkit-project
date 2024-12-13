package com.capstone.sampahin.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sampahin.databinding.FragmentTopicsBinding
import com.capstone.sampahin.ui.chat.adapter.TopicsAdapter

class TopicsFragment : Fragment() {
    private lateinit var binding: FragmentTopicsBinding
    private val viewModel: ChatViewModel by viewModels()
    private val topicsAdapter by lazy {
        TopicsAdapter { selectedTopic ->
            startChatScreen(selectedTopic)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchTopics()
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val decoration = DividerItemDecoration(
            binding.rvTopics.context,
            linearLayoutManager.orientation
        )

        with(binding.rvTopics) {
            layoutManager = linearLayoutManager
            adapter = topicsAdapter
            addItemDecoration(decoration)
        }
    }

    private fun observeViewModel() {
        viewModel.topics.observe(viewLifecycleOwner) { topics ->
            topicsAdapter.submitList(topics)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun startChatScreen(topicTitle: String) {
        val action = TopicsFragmentDirections.actionTopicsFragmentToChatFragment(topicTitle)
        Navigation.findNavController(requireView()).navigate(action)
    }
}