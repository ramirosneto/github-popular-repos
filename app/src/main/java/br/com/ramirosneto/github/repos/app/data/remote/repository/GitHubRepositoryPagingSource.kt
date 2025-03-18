package br.com.ramirosneto.github.repos.app.data.remote.repository

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepositoryPagingSource @Inject constructor(
    private val repository: GitHubRepository
) : RxPagingSource<Int, RepositoryDTO>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, RepositoryDTO>> {
        val page = params.key ?: 1
        return repository.getRepositories(QUERY, DEFAULT_SORT, DEFAULT_PER_PAGE, page)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, RepositoryDTO>> { response ->
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.isNullOrEmpty()) null else page + 1
                )
            }
            .onErrorReturn { throwable ->
                LoadResult.Error(throwable)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryDTO>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val QUERY = "language:Java"
        private const val DEFAULT_SORT = "stars"
        private const val DEFAULT_PER_PAGE = "20"
    }
}
