package com.ed.mygithub.reposlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ed.mygithub.databinding.ReposItemBinding

class ReposAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ShortRepo> = arrayListOf()

    fun setItems(items: List<ShortRepo>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        val binding = ReposItemBinding.inflate(inflater, parent, false)

        return RepoViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RepoViewHolder).bindStatic(items[position])
    }
}