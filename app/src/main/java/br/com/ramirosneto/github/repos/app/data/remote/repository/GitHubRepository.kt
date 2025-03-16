package br.com.ramirosneto.github.repos.app.data.remote.repository

import br.com.ramirosneto.github.repos.app.data.remote.api.GitHubApi
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val api: GitHubApi) {

    fun searchRepositories(query: String, sort: String, page: Int) =
        api.searchRepositories(query, sort, page)

    fun getPullRequests(owner: String, repo: String) =
        api.getPullRequests(owner, repo)
}
