package com.example.paging.network

import com.example.paging.models.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("user/repos")
    suspend fun getRepos(@Query(value = "page") page: Int,
                         @Query(value = "per_page") perPage: Int) : List<Repo>

    // Unused now, but we could use this with the RxPagedListBuilder
    // https://developer.android.com/reference/androidx/paging/RxPagedListBuilder
    @GET("user/repos")
    fun getReposRx(@Query(value = "page") page: Int,
                         @Query(value = "per_page") perPage: Int) : Single<List<Repo>>
}