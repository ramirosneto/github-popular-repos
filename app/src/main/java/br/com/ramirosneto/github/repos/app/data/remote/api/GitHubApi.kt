package br.com.ramirosneto.github.repos.app.data.remote.api

import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubPullRequest
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("per_page") perPage: String,
        @Query("page") page: Int
    ): Single<GitHubResponse>

    @GET("repos/{owner}/{repo}/pulls")
    fun getPullRequests(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<GitHubPullRequest>>
}
