package com.gramzin.mvvmproject.model

import com.github.javafaker.Faker
import com.gramzin.mvvmproject.UserNotFoundException
import java.util.Collections

typealias UserListener = (users: List<User>) -> Unit

object UserService {
    private var users = mutableListOf<User>()
    private var listeners = mutableSetOf<UserListener>()

    init {
        val imagesUrl = "https://randomuser.me/api/portraits/women/"
        val faker = Faker()
        users = (1..100).map {
            User(
                it.toLong(),
                faker.name().name(),
                faker.company().name(),
                imagesUrl + "${it % 50}.jpg"
            )
        }.toMutableList()
    }

    fun getUsers(): List<User> = users

    fun getUserDetailsById(id: Long) : UserDetails{
        val user = users.firstOrNull{it.id == id} ?: throw UserNotFoundException()
        val content = Faker.instance().lorem().paragraphs(3).joinToString("\n\n")
        return UserDetails(user, content)
    }

    fun removeUser(user: User){
        val indexFirst = users.indexOfFirst { it.id == user.id }
        if (indexFirst != -1){
            users.removeAt(indexFirst)
            notifyChanges()
        }
    }

    fun moveUser(user: User, moveBy: Int){
        val indexFirst = users.indexOfFirst { it.id == user.id }
        if (indexFirst != -1){
            Collections.swap(users, indexFirst, indexFirst + moveBy)
            notifyChanges()
        }
    }

    fun addListener(listener: UserListener){
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UserListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach { it.invoke(users) }
    }

}