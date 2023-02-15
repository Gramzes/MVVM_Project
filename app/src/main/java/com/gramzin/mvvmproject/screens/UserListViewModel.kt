package com.gramzin.mvvmproject.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gramzin.mvvmproject.model.User
import com.gramzin.mvvmproject.model.UserListener
import com.gramzin.mvvmproject.model.UserService

class UserListViewModel: ViewModel() {
    private var _users =  MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users

    private val listener: UserListener = {
        _users.value = it
    }

    init {
        UserService.addListener(listener)
    }

    override fun onCleared() {
        super.onCleared()
        UserService.removeListener(listener)
    }

    fun moveUser(user: User, moveBy: Int){
        UserService.moveUser(user, moveBy)
    }

    fun removeUser(user: User){
        UserService.removeUser(user)
    }


}