package com.gramzin.mvvmproject.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gramzin.mvvmproject.model.UserDetails
import com.gramzin.mvvmproject.model.UserService

class UserDetailsViewModel: ViewModel() {
    private var _user = MutableLiveData<UserDetails>()
    var user: LiveData<UserDetails> = _user

    fun loadUser(userId: Long) {
        _user.value = UserService.getUserDetailsById(userId)
    }

    fun deleteUser(){
        val userDetails = _user.value ?: return
        UserService.removeUser(userDetails.user)
    }
}