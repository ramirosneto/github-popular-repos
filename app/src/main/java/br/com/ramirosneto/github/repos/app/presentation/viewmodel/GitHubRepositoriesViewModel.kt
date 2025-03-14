package br.com.ramirosneto.github.repos.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.ramirosneto.github.repos.app.data.remote.model.Repository
import br.com.ramirosneto.github.repos.app.data.remote.repository.GitHubRepository
import br.com.ramirosneto.github.repos.app.data.remote.repository.RepositoryPagingSource
import io.reactivex.Flowable
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {

    fun getRepositories(): Flowable<PagingData<Repository>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { RepositoryPagingSource(repository) }
        ).flowable
    }
}