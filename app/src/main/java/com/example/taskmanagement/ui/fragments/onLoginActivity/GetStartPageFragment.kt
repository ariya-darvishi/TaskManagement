package com.example.taskmanagement.ui.fragments.onLoginActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.FragmentGetStartPageBinding

class GetStartPageFragment : Fragment() {

    private lateinit var binding: FragmentGetStartPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_get_start_page, container, false)
        binding.fragmentGetStart = this
        return binding.root
    }

    fun createNewTaskBtnClickListener(view: View){
        view.findNavController().navigate(R.id.action_getStartPageFragment_to_selectLoginOrSignupPageFragment)
    }

}