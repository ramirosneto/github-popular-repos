package br.com.ramirosneto.github.repos.app.data.remote.repository

import br.com.ramirosneto.github.repos.app.data.remote.api.GitHubApi
import br.com.ramirosneto.github.repos.app.presentation.model.PullRequestDTO
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import br.com.ramirosneto.github.repos.app.util.toPullRequestDTO
import br.com.ramirosneto.github.repos.app.util.toRepositoryDTO
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val api: GitHubApi
) : GitHubRepository {

    override fun getRepositories(
        query: String,
        sort: String,
        perPage: String,
        page: Int
    ): Single<List<RepositoryDTO>> {
        val response = api.getRepositories(query, sort, perPage, page)
        return response.map {
            it.items.map { it.toRepositoryDTO() }
        }
    }

    override fun getPullRequests(
        owner: String,
        repo: String
    ): Single<List<PullRequestDTO>> {
        val response = api.getPullRequests(owner, repo)
        return response.map {
            it.map { it.toPullRequestDTO() }
        }
    }
}
