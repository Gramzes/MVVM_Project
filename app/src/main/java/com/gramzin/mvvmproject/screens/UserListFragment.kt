package com.gramzin.mvvmproject.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gramzin.mvvmproject.R
import com.gramzin.mvvmproject.UserActionListener
import com.gramzin.mvvmproject.UsersAdapter
import com.gramzin.mvvmproject.databinding.UserListFragmentBinding
import com.gramzin.mvvmproject.model.User

class UserListFragment: Fragment() {
    private lateinit var binding: UserListFragmentBinding
    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserListFragmentBinding.inflate(inflater, container, false)

        val listener = object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
               viewModel.moveUser(user, moveBy)
            }

            override fun onUserRemove(user: User) {
                viewModel.removeUser(user)
            }

            override fun onUserDetails(user: User) {
                val bundle = bundleOf(UserDetailsFragment.USER_ID_KEY to user.id)
                val controller = findNavController()
                findNavController().navigate(R.id.action_userListFragment_to_userDetailsFragment, bundle)
            }

        }
        val usersAdapter = UsersAdapter(listener)

        with(binding.usersRcView) {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.users.observe(viewLifecycleOwner, Observer {
            usersAdapter.addUsers(it)
        })

        return binding.root
    }
}