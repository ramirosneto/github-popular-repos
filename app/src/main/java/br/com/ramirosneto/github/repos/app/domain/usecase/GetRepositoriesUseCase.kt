package br.com.ramirosneto.github.repos.app.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import br.com.ramirosneto.github.repos.app.data.remote.repository.GitHubRepository
import br.com.ramirosneto.github.repos.app.data.remote.repository.RepositoryPagingSource
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import javax.inject.Inject

class GetRepositoriesUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    operator fun invoke(): Pager<Int, RepositoryDTO> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = ENABLED_PLACEHOLDERS,
                initialLoadSize = INITIAL_LOAD_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            )
        ) {
            RepositoryPagingSource(repository)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val ENABLED_PLACEHOLDERS = false
        private const val INITIAL_LOAD_SIZE = 30
        private const val PREFETCH_DISTANCE = 10
    }
}
