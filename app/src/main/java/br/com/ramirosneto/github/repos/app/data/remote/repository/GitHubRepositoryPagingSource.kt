package br.com.ramirosneto.github.repos.app.data.remote.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.ramirosneto.github.repos.app.data.remote.model.Repository
import retrofit2.HttpException
import java.io.IOException

class RepositoryPagingSource(
    private val repository: GitHubRepository
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val page = params.key ?: 1
        return try {
            val response =
                repository.searchRepositories("language:Kotlin", "stars", page).blockingGet()
            LoadResult.Page(
                data = response.repositories,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.repositories.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
