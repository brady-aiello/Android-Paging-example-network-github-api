package com.example.paging.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.paging.network.GitHubService
import com.example.paging.models.Repo
import kotlinx.coroutines.CoroutineScope

class GithubRepoDataSourceFactory(val scope: CoroutineScope,
                                  val gitHubService: GitHubService)
    : DataSource.Factory<Int, Repo>() {

    val sourceLiveData = MutableLiveData<GithubRepoDataSource>()
    lateinit var latestSource: GithubRepoDataSource

    override fun create(): DataSource<Int, Repo> {
        latestSource = GithubRepoDataSource(scope, gitHubService)
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}