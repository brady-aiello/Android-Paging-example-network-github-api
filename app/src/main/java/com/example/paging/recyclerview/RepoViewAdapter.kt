package com.example.paging.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.paging.R
import com.example.paging.databinding.RepoViewBinding
import com.example.paging.models.Repo

class RepoViewAdapter(diffCallback: DiffUtil.ItemCallback<Repo>): PagedListAdapter<Repo, RepoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val repoViewBinding : RepoViewBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.repo_view, parent, false)
        return RepoViewHolder(repoViewBinding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.repoViewBinding.repo = getItem(position)
    }
}