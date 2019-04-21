package com.ed.mygithub.reposlist

import androidx.recyclerview.widget.RecyclerView
import com.ed.mygithub.databinding.ReposItemBinding

class RepoViewHolder(val binding: ReposItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindStatic(item: ShortRepo) {
        binding.item = item
    }
}