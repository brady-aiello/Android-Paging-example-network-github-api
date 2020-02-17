package com.example.paging.main

import androidx.lifecycle.ViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.paging.datasource.GithubRepoDataSourceFactory
import com.example.paging.models.Repo
import com.example.paging.network.GitHubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel: ViewModel() {

    private val TAG = MainViewModel::class.java.toString()

    private val url = "https://api.github.com/"
    private val loggingInterceptor = HttpLoggingInterceptor()
    private var gitHubService : GitHubService
    private var httpClient : OkHttpClient
    private val githubRepoDataSourceFactory : GithubRepoDataSourceFactory
    private val pagedListConfig: PagedList.Config
    var repoList: LiveData<PagedList<Repo>>

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()

                    val requestBuilder = original.newBuilder()
                        .header("Authorization",
                             "Bearer <PUT YOUR GITHUB TOKEN HERE>")
                    val request = requestBuilder.build()
                    return chain.proceed(request)
                    TODO("PUT YOUR GITHUB TOKEN IN THE HEADER ABOVE")
                }
            })
            .build()

        gitHubService = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // To convert to Observables
            .addConverterFactory(MoshiConverterFactory.create()) // To convert JSON to CatFact / CatFactJavaModel
            .build()
            .create(GitHubService::class.java)

        githubRepoDataSourceFactory =
            GithubRepoDataSourceFactory(viewModelScope, gitHubService)

        pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            // This determines the number of items ithubRepoDataSource.loadInitial() will request.
            // Set the initial load size to your liking. 10 will not fill the screen, so
            // initially, a couple calls to GithubRepoDataSource.loadRange() will also be made.
            .setInitialLoadSizeHint(10)
            // Items requested in GithubRepoDataSource.loadRange()
            .setPageSize(10)
            .build()

        repoList = LivePagedListBuilder(githubRepoDataSourceFactory, pagedListConfig).build()
        // We can also now convert a DataSourceFactory to LiveData
        // with the following extension function:
        //repoList = githubRepoDataSourceFactory.toLiveData(pageSize = 15)
    }
}