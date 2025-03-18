package br.com.ramirosneto.github.repos.app.data.remote.repository

import br.com.ramirosneto.github.repos.app.data.remote.api.GitHubApi
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubPullRequest
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import br.com.ramirosneto.github.repos.app.util.toRepositoryDTO
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val api: GitHubApi) : GitHubRepository {

    override fun searchRepositories(
        query: String,
        sort: String,
        page: Int
    ): Single<List<RepositoryDTO>> {
        val response = api.searchRepositories(query, sort, page)
        return response.map {
            it.items.map { it.toRepositoryDTO() }
        }
    }

    override fun getPullRequests(
        owner: String,
        repo: String
    ): Single<List<GitHubPullRequest>> {
        return api.getPullRequests(owner, repo)
    }
}
