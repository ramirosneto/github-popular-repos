package br.com.ramirosneto.github.repos.app.data.remote.repository

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryOwner
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepositoryPagingSourceTest {
    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var pagingSource: RepositoryPagingSource

    @Before
    fun setUp() {
        gitHubRepository = mockk()
        pagingSource = RepositoryPagingSource(gitHubRepository)
    }

    @Test
    fun `loadSingle - successful loading`() {
        val mockData = listOf(
            RepositoryDTO(
                id = 1,
                name = "Repo1",
                description = "Description Repo1",
                forksCount = 1231,
                starsCount = 523,
                owner = RepositoryOwner(login = "kasdi", "https://www.google.com/avatar")
            ),
            RepositoryDTO(
                id = 2,
                name = "Repo2",
                description = "Description Repo2",
                forksCount = 523,
                starsCount = 574523,
                owner = RepositoryOwner(login = "kasd12i", "https://www.google.com/avatar")
            )
        )
        val query = "language:Java"
        val sort = "stars"
        val itemsPerPage = "20"
        val page = 1
        every { gitHubRepository.getRepositories(query, sort, itemsPerPage, page) } returns
                Single.just(mockData)

        val result =
            pagingSource.loadSingle(PagingSource.LoadParams.Refresh(page, 20, false))
                .blockingGet()

        verify { gitHubRepository.getRepositories(query, sort, itemsPerPage, page) }
        assertEquals(
            LoadResult.Page(
                data = mockData,
                prevKey = null,
                nextKey = page + 1
            ), result
        )
    }

    @Test
    fun `loadSingle - empty data`() {
        val query = "language:Java"
        val sort = "stars"
        val itemsPerPage = "20"
        val page = 1
        every { gitHubRepository.getRepositories(query, sort, itemsPerPage, page) } returns
                Single.just(emptyList())

        val result =
            pagingSource.loadSingle(PagingSource.LoadParams.Refresh(page, 20, false))
                .blockingGet()

        verify { gitHubRepository.getRepositories(query, sort, itemsPerPage, page) }
        assertEquals(
            LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            ), result
        )
    }

    /*@Test
    fun `getRefreshKey - returns correct key`() {
        // Arrange
        val anchorPosition = 5
        val state = PagingState(
            listOf(),
            anchorPosition,
            PagingSource.LoadConfig(20, 20, false, 20, 0),
            0
        )

        // Act
        val refreshKey = pagingSource.getRefreshKey(state)

        // Assert
        assertEquals(anchorPosition, refreshKey)
    }*/

    @Test
    fun `loadSingle - successful loading multiple pages`() {
        val mockData1 = listOf(
            RepositoryDTO(
                id = 1,
                name = "Repo1",
                description = "Description Repo1",
                forksCount = 1231,
                starsCount = 523,
                owner = RepositoryOwner(login = "kasdi", "https://www.google.com/avatar")
            )
        )
        val mockData2 = listOf(
            RepositoryDTO(
                id = 2,
                name = "Repo2",
                description = "Description Repo2",
                forksCount = 523,
                starsCount = 574523,
                owner = RepositoryOwner(login = "kasd12i", "https://www.google.com/avatar")
            )
        )
        val query = "language:Java"
        val sort = "stars"
        val itemsPerPage = "20"

        every { gitHubRepository.getRepositories(query, sort, itemsPerPage, 1) } returns
                Single.just(mockData1)
        every { gitHubRepository.getRepositories(query, sort, itemsPerPage, 2) } returns
                Single.just(mockData2)

        // Act
        val resultPage1 =
            pagingSource.loadSingle(PagingSource.LoadParams.Refresh(1, 20, false))
                .blockingGet()

        // Assert
        verify { gitHubRepository.getRepositories(query, sort, itemsPerPage, 1) }
        assertEquals(
            LoadResult.Page(
                data = mockData1,
                prevKey = null,
                nextKey = 2
            ), resultPage1
        )
        // Act
        val resultPage2 =
            pagingSource.loadSingle(PagingSource.LoadParams.Append(2, 20, false))
                .blockingGet()

        // Assert
        verify { gitHubRepository.getRepositories(query, sort, itemsPerPage, 2) }
        assertEquals(
            LoadResult.Page(
                data = mockData2,
                prevKey = 1,
                nextKey = 3
            ), resultPage2
        )
    }
}
