package com.example.aplikasigithubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasigithubuser.databinding.ItemFollowBinding
import com.example.aplikasigithubuser.model.UserFollowing

class ListFollowingAdapter(private val listFollowing: ArrayList<UserFollowing>) : RecyclerView.Adapter<ListFollowingAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(following: UserFollowing) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(following.avatar)
                    .apply(RequestOptions().override(100,100))
                    .into(imgAvatar)

                tvUsername.text = following.userName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int = listFollowing.size
}