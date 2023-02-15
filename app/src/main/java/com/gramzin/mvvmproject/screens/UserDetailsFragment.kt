package com.gramzin.mvvmproject.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gramzin.mvvmproject.databinding.UserDetailsFragmentBinding

class UserDetailsFragment: Fragment() {
    private lateinit var binding: UserDetailsFragmentBinding
    private val viewModel: UserDetailsViewModel by viewModels()

    companion object{
        const val USER_ID_KEY = "USER_ID_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.loadUser(requireArguments().getLong(USER_ID_KEY))

        binding = UserDetailsFragmentBinding.inflate(inflater, container, false)
        binding.userDetails = viewModel.user.value
        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.userDetails = it
        })

        binding.removeBtn.setOnClickListener {
            viewModel.deleteUser()
            findNavController().navigateUp()
        }
        return binding.root
    }
}