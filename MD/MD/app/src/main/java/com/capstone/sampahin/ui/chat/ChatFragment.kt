package com.capstone.sampahin.ui.chat

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sampahin.R
import com.capstone.sampahin.data.Message
import com.capstone.sampahin.data.chat.ChatRequest
import com.capstone.sampahin.databinding.FragmentChatBinding
import com.capstone.sampahin.ui.chat.adapter.ChatHistoryAdapter
import com.capstone.sampahin.ui.chat.adapter.QuestionSuggestionsAdapter

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatAdapter: ChatHistoryAdapter
    private var questionAdapter: QuestionSuggestionsAdapter? = null
    private val viewModel: ChatViewModel by viewModels()
    private val args: ChatFragmentArgs by navArgs()

    private var topicSuggestedQuestions: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            String.format(getString(R.string.fragment_qa_title), args.topicTitle)

        viewModel.fetchQuestionsByTopic(args.topicTitle)

        viewModel.questions.observe(viewLifecycleOwner) { questions ->
            topicSuggestedQuestions = questions
            questionAdapter?.submitList(questions)
            if (questions.isNotEmpty()) {
                if (questionAdapter == null) {
                    initQuestionSuggestionsRecyclerView()
                }
                binding.rvQuestionSuggestions.visibility = View.VISIBLE
                binding.tvSuggestion.visibility = View.VISIBLE
            } else {
                Log.d("ChatFragment", "No questions found for topic: ${args.topicTitle}")
                binding.rvQuestionSuggestions.visibility = View.GONE
                binding.tvSuggestion.visibility = View.GONE
            }
        }


        initChatHistoryRecyclerView()
        initQuestionSuggestionsRecyclerView()

        binding.tietQuestion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.ibSend.isClickable = !s.isNullOrEmpty()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.ibSend.setOnClickListener {
            if (it.isClickable && binding.tietQuestion.text?.isNotEmpty() == true) {
                val question = binding.tietQuestion.text.toString()
                binding.tietQuestion.text?.clear()

                chatAdapter.addMessage(Message(question, true))

                binding.progressBar.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).post {
                    val topic = args.topicTitle
                    viewModel.fetchAnswers(ChatRequest(listOf(topic), listOf(question)))
                    binding.progressBar.visibility = View.GONE
                }

                hideKeyboard(it)
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.please_enter_the_question_first), Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.answers.observe(viewLifecycleOwner) { answer ->
            val sadEmoji = "\uD83D\uDE41"
            answer?.let {
                if (!it.answers.isNullOrEmpty()) {
                    chatAdapter.addMessage(Message(it.answers[0], false))
                    binding.rvChatHistory.scrollToPosition(chatAdapter.itemCount - 1)
                } else {
                    chatAdapter.addMessage(Message(getString(R.string.failed_message, sadEmoji), false))
                }
            } ?: run {
                chatAdapter.addMessage(Message(getString(R.string.failed_message, sadEmoji), false))
            }
        }


        setButtonSuggestion()
    }

    private fun initChatHistoryRecyclerView() {
        val smileEmoji = "\uD83D\uDE03"
        val historyLayoutManager = LinearLayoutManager(context)
        binding.rvChatHistory.layoutManager = historyLayoutManager
        chatAdapter = ChatHistoryAdapter()
        binding.rvChatHistory.adapter = chatAdapter
        chatAdapter.addMessage(Message(getString(R.string.initial_chat, smileEmoji), false))
    }

    private fun initQuestionSuggestionsRecyclerView() {
        if (topicSuggestedQuestions.isNotEmpty()) {
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

            questionAdapter = QuestionSuggestionsAdapter(
                topicSuggestedQuestions,
                object : QuestionSuggestionsAdapter.OnOptionClicked {
                    override fun onOptionClicked(optionID: Int) {
                        setQuestion(optionID)
                    }
                }
            )

            binding.rvQuestionSuggestions.apply {
                adapter = questionAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(decoration)
            }
        } else {
            binding.tvSuggestion.visibility = View.GONE
            binding.rvQuestionSuggestions.visibility = View.GONE
        }
    }


    private fun setButtonSuggestion() {
        val buttonSuggestion = binding.buttonSuggestion
        val rvQuestionSuggestions = binding.rvQuestionSuggestions
        val progressBar = binding.progressBar

        buttonSuggestion.isEnabled = progressBar.visibility == View.GONE

        buttonSuggestion.setOnClickListener {
            if (progressBar.visibility == View.GONE) {
                val isVisible = rvQuestionSuggestions.visibility == View.VISIBLE
                rvQuestionSuggestions.visibility = if (isVisible) View.GONE else View.VISIBLE
                buttonSuggestion.setImageResource(
                    if (isVisible) R.drawable.baseline_keyboard_arrow_down_24
                    else R.drawable.baseline_keyboard_arrow_up_24
                )
            }
        }
    }

    private fun setQuestion(position: Int) {
        binding.tietQuestion.setText(topicSuggestedQuestions[position])
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        binding.tietQuestion.addTextChangedListener(null)
        super.onDestroyView()
    }
}