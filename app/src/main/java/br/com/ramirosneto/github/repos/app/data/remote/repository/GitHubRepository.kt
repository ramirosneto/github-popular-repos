package br.com.ramirosneto.github.repos.app.data.remote.repository

import br.com.ramirosneto.github.repos.app.data.remote.api.GitHubApi
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubResponse
import io.reactivex.Single
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val api: GitHubApi) {

    fun searchRepositories(query: String, sort: String, page: Int): Single<GitHubResponse> {
        return api.searchRepositories(query, sort, page)
    }
}
