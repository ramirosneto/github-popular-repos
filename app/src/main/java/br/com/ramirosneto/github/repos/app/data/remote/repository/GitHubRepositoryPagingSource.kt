package br.com.ramirosneto.github.repos.app.data.remote.repository

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryPagingSource(
    private val repository: GitHubRepositoryImpl
) : RxPagingSource<Int, RepositoryDTO>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, RepositoryDTO>> {
        val page = params.key ?: 1
        return repository.searchRepositories(QUERY, DEFAULT_SORT, page)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, RepositoryDTO>> { response ->
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.isEmpty()) null else page + 1
                )
            }
            .onErrorReturn { throwable ->
                LoadResult.Error(throwable)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val QUERY = "language:Java"
        private const val DEFAULT_SORT = "stars"
    }
}
