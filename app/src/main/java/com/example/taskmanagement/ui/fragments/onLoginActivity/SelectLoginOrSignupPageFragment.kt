package com.example.taskmanagement.ui.fragments.onLoginActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.example.taskmanagement.R
import com.example.taskmanagement.databinding.FragmentGetStartPageBinding
import com.example.taskmanagement.databinding.FragmentSelectLoginOrSignupPageBinding

class SelectLoginOrSignupPageFragment : Fragment() {


    private lateinit var binding: FragmentSelectLoginOrSignupPageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupImageSlider()

    }

    private fun setupImageSlider() {
        val images = ArrayList<SlideModel>()
        images.add(SlideModel(R.drawable._view_pager_1))
        images.add(SlideModel(R.drawable._view_pager_2))
        binding.imageSlider.setImageList(images)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_login_or_signup_page, container, false)
        binding.fragmentLoginOrSignIn = this
        return binding.root
    }

    fun onSignInBtnClickListener(view: View){
        view.findNavController().navigate(R.id.action_selectLoginOrSignupPageFragment_to_signupFragment)
    }

    fun onLogInBtnClickListener(view: View){
        view.findNavController().navigate(R.id.action_selectLoginOrSignupPageFragment_to_loginFragment)
    }
}