package com.gramzin.mvvmproject

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gramzin.mvvmproject.databinding.UserItemBinding
import com.gramzin.mvvmproject.model.User
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

interface UserActionListener{

    fun onUserMove(user: User, moveBy: Int)

    fun onUserRemove(user: User)

    fun onUserDetails(user: User)
}

class UsersAdapter(private var listener: UserActionListener):
    RecyclerView.Adapter<UsersAdapter.UserHolder>(), View.OnClickListener {

    private var users = mutableListOf<User>()

    class UserHolder(var binding: UserItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreBtn.setOnClickListener(this)

        return UserHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val currentUser = users[position]
        holder.binding.user = currentUser
    }

    fun addUsers(users: Collection<User>){
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun onClick(view: View) {
        val user = view.tag as User
        when(view.id){
            R.id.moreBtn ->{
                showPopupMenu(view)
            }
            else ->{
                listener.onUserDetails(user)
            }
        }
    }

    private fun showPopupMenu(view: View){
        val context = view.context
        val popupMenu = PopupMenu(context, view)
        val user = view.tag as User
        val position = users.indexOfFirst { it.id == user.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.resources.getString(R.string.move_up)).apply {
            isEnabled = position > 0
        }

        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.resources.getString(R.string.move_down)).apply {
            isEnabled = position < users.size - 1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.resources.getString(R.string.remove))


        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                ID_MOVE_UP -> listener.onUserMove(user, -1)
                ID_MOVE_DOWN -> listener.onUserMove(user, 1)
                ID_REMOVE -> listener.onUserRemove(user)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object{
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}

@BindingAdapter("imageUrl", "error")
fun loadImage(view: ImageView, url: String, error: Drawable) {
    Picasso.get()
        .load(url)
        .error(error)
        .transform(CropCircleTransformation())
        .into(view)
}