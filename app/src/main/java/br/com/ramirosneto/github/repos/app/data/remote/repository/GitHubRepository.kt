package br.com.ramirosneto.github.repos.app.data.remote.repository

import br.com.ramirosneto.github.repos.app.presentation.model.PullRequestDTO
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import io.reactivex.rxjava3.core.Single

interface GitHubRepository {

    fun getRepositories(
        query: String,
        sort: String,
        perPage: String,
        page: Int
    ): Single<List<RepositoryDTO>>

    fun getPullRequests(
        owner: String,
        repo: String
    ): Single<List<PullRequestDTO>>
}
