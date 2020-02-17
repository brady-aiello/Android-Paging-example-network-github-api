package com.example.paging.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.paging.databinding.RepoViewBinding


/*
 * Passing the RepoViewBinding lets us reference any view element in the layout without findViewById.
 */
class RepoViewHolder(val repoViewBinding: RepoViewBinding): RecyclerView.ViewHolder(repoViewBinding.root)