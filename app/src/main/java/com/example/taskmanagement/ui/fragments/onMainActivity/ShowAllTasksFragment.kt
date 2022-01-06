package com.example.taskmanagement.ui.fragments.onMainActivity

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.R
import com.example.taskmanagement.adapters.ShowAllTasksRecyclerViewAdapter
import com.example.taskmanagement.databinding.FragmentShowAllTasksBinding
import com.example.taskmanagement.utils.RecyclerViewMarginItemDecoration
import com.example.taskmanagement.utils.setupRecyclerView
import com.example.taskmanagement.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.text.Editable
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.taskmanagement.data.entities.Task
import java.util.*


@AndroidEntryPoint
class ShowAllTasksFragment : Fragment() {

    private lateinit var binding: FragmentShowAllTasksBinding
    private val viewModel: MainViewModel by viewModels()

    private val recyclerAdapter = ShowAllTasksRecyclerViewAdapter()

    private var filteredList = mutableListOf<Task>()
    var allData = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =DataBindingUtil.inflate(inflater, R.layout.fragment_show_all_tasks, container, false)

        binding.fragmentShowAllTasks = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBox()
        getAllTasks()


    }

    private fun setupSearchBox() {

        binding.searchBoxAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                checkForHideCleanerSearchTextImageView()
            }
            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })
    }

    private fun filter(name: String) {
        filteredList.clear()
        for (item: Task in allData) {
            if (item.taskTitle.lowercase(Locale.getDefault())
                    .contains(name.lowercase(Locale.getDefault())) || item.taskTitle
                    .lowercase(Locale.getDefault()).contains(name.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(item)
            }
        }
        recyclerAdapter.updateData(filteredList)
        recyclerAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                if (recyclerAdapter.itemCount === 0) {
                    binding.itemNotFoundTextView.visibility = View.VISIBLE
                } else {
                    binding.itemNotFoundTextView.visibility = View.GONE
                }
            }
        })
    }


    private fun getAllTasks() {
        viewModel.allTasks.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.differ.submitList(it)
            allData = it.toMutableList()
            initRecyclerView()

        })
    }

    private fun initRecyclerView() {
        binding.showAllTasksRecyclerView.setupRecyclerView(
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            recyclerAdapter,
            RecyclerViewMarginItemDecoration(20, 20, 0,0)
        )
    }

    private fun checkForHideCleanerSearchTextImageView() {
        if (binding.searchBoxAutoCompleteTextView.text.isEmpty()) {
            binding.cleanerSearchInputTextImageView.visibility = View.GONE
            hideAndroidKeyBoard()
        }
        else
            binding.cleanerSearchInputTextImageView.visibility = View.VISIBLE

    }

    fun cleanSearchInputText(){
        hideAndroidKeyBoard()
        binding.searchBoxAutoCompleteTextView.text = null
    }

    private fun hideAndroidKeyBoard(){
        val androidKeaBoard =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        androidKeaBoard.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun onBackToHomeClickListener(view: View){
        Navigation.findNavController(view).popBackStack()
    }

}