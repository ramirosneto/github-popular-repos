package br.com.ramirosneto.github.repos.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.ramirosneto.github.repos.app.data.remote.repository.GitHubRepositoryImpl
import br.com.ramirosneto.github.repos.app.data.remote.repository.RepositoryPagingSource
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitHubRepositoriesViewModel @Inject constructor(
    private val repository: GitHubRepositoryImpl
) : ViewModel() {
    private val pager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = INITIAL_LOAD_SIZE
        )
    ) {
        RepositoryPagingSource(repository)
    }

    val state: Flow<PagingData<RepositoryDTO>> =
        pager.flow.cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_LOAD_SIZE = 40
    }
}