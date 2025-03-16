package br.com.ramirosneto.github.repos.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import br.com.ramirosneto.github.repos.app.data.remote.model.Repository
import br.com.ramirosneto.github.repos.app.data.remote.repository.GitHubRepository
import br.com.ramirosneto.github.repos.app.data.remote.repository.RepositoryPagingSource
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class GitHubRepositoriesViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    fun getRepositories(): Flowable<PagingData<Repository>> {
        return Pager(PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false)) {
            RepositoryPagingSource(repository)
        }.flowable
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}