package com.gramzin.mvvmproject.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gramzin.mvvmproject.UserActionListener
import com.gramzin.mvvmproject.UsersAdapter
import com.gramzin.mvvmproject.databinding.ActivityMainBinding
import com.gramzin.mvvmproject.model.User
import com.gramzin.mvvmproject.model.UserListener
import com.gramzin.mvvmproject.model.UserService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}