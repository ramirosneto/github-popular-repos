package br.com.ramirosneto.github.repos.app.data.remote.repository

import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubPullRequest
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import io.reactivex.rxjava3.core.Single

interface GitHubRepository {

    fun searchRepositories(query: String, sort: String, page: Int): Single<List<RepositoryDTO>>

    fun getPullRequests(owner: String, repo: String): Single<List<GitHubPullRequest>>
}
