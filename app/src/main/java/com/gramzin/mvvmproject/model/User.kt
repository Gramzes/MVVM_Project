package com.gramzin.mvvmproject.model

data class User(var id: Long, var name: String, var company: String, var photoURL: String)

data class UserDetails(var user: User, var content: String)