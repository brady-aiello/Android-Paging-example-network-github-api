package com.example.paging.datasource

import androidx.paging.PositionalDataSource
import com.example.paging.network.GitHubService
import com.example.paging.models.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// The scope passed in here is viewModelScope, which is lifecycle-aware.
// The ViewModel will cancel when it is destroyed.
class GithubRepoDataSource(val scope: CoroutineScope,
                           val gitHubService: GitHubService
): PositionalDataSource<Repo>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Repo>) {
        scope.launch(Dispatchers.IO) {
            val nextPageIndex = (params.startPosition / params.loadSize) + 1
            val repos = gitHubService.getRepos(nextPageIndex, params.loadSize)
            callback.onResult(repos)
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Repo>) {
        scope.launch(Dispatchers.IO) {
            val repos = gitHubService.getRepos(params.requestedStartPosition, params.pageSize)
            callback.onResult(repos, params.requestedStartPosition)
        }
    }
}