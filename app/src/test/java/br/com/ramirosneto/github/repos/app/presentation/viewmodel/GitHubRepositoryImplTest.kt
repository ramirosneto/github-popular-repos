package br.com.ramirosneto.github.repos.app.presentation.viewmodel

import br.com.ramirosneto.github.repos.app.data.remote.api.GitHubApi
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubOwner
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubPullRequest
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubRepositoryData
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubResponse
import br.com.ramirosneto.github.repos.app.data.remote.repository.GitHubRepository
import br.com.ramirosneto.github.repos.app.data.remote.repository.GitHubRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GitHubRepositoryImplTest {

    private lateinit var api: GitHubApi
    private lateinit var repository: GitHubRepository

    @Before
    fun setUp() {
        api = mockk()
        repository = GitHubRepositoryImpl(api)
    }

    @Test
    fun `getRepositories - success`() {
        val query = "language:Java"
        val sort = "stars"
        val itemsPerPage = "20"
        val page = 1

        val repositoryItemResponse = GitHubRepositoryData(
            id = 65932,
            name = "Test Repo",
            description = "Description Test Repo",
            stars = 12100,
            forks = 505,
            owner = GitHubOwner(
                id = 625,
                login = "TestUser",
                avatarUrl = "https://www.google.com/avatar"
            )
        )

        val repositoryResponse = GitHubResponse(
            totalCount = 658,
            incompleteResults = false,
            items = listOf(repositoryItemResponse)
        )
        every { api.getRepositories(query, sort, itemsPerPage, page) } returns Single.just(
            repositoryResponse
        )

        val result = repository.getRepositories(query, sort, itemsPerPage, page).blockingGet()

        verify { api.getRepositories(query, sort, itemsPerPage, page) }
        assertEquals(1, result.size)
        val firstRepo = result[0]
        assertEquals(65932, firstRepo.id)
        assertEquals("Test Repo", firstRepo.name)
        assertEquals("Description Test Repo", firstRepo.description)
        assertEquals(12100, firstRepo.starsCount)
        assertEquals(505, firstRepo.forksCount)
        assertEquals("TestUser", firstRepo.owner.login)
        assertEquals("https://www.google.com/avatar", firstRepo.owner.avatarUrl)
    }

    @Test
    fun `getRepositories - error`() {
        val query = "language:Java"
        val sort = "stars"
        val itemsPerPage = "20"
        val page = 1
        val errorMessage = "Network Error"
        val error = RuntimeException(errorMessage)
        every { api.getRepositories(query, sort, itemsPerPage, page) } returns Single.error(error)

        val exception = assertFailsWith<RuntimeException> {
            repository.getRepositories(query, sort, itemsPerPage, page).blockingGet()
        }

        verify { api.getRepositories(query, sort, itemsPerPage, page) }
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `getPullRequests - success`() {
        val owner = "Test User"
        val repo = "Test Repo"
        val pullRequestResponse = GitHubPullRequest(
            title = "Test Pull Request",
            body = "Test Body",
            url = "https://www.github.com/repo/125",
            createdAt = "2025-03-17T22:14:15Z",
            user = GitHubOwner(
                id = 625,
                login = "TestUser",
                avatarUrl = "https://www.google.com/avatar"
            )
        )

        every { api.getPullRequests(owner, repo) } returns
                Single.just(listOf(pullRequestResponse))

        val result = repository.getPullRequests(owner, repo).blockingGet()

        verify { api.getPullRequests(owner, repo) }
        assertEquals(1, result.size)
        val firstPullRequest = result[0]
        assertEquals("Test Pull Request", firstPullRequest.title)
        assertEquals("Test Body", firstPullRequest.description)
        assertEquals("https://www.github.com/repo/125", firstPullRequest.url)
        assertEquals("TestUser", firstPullRequest.owner.login)
        assertEquals("https://www.google.com/avatar", firstPullRequest.owner.avatarUrl)
    }

    @Test
    fun `getPullRequests - error`() {
        val owner = "Test User"
        val repo = "Test Repo"
        val errorMessage = "Network Error"
        val error = RuntimeException(errorMessage)
        every { api.getPullRequests(owner, repo) } returns Single.error(error)

        val exception = assertFailsWith<RuntimeException> {
            repository.getPullRequests(owner, repo).blockingGet()
        }

        verify { api.getPullRequests(owner, repo) }
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `getRepositories - Not Empty List`() {
        val query = "language:Java"
        val sort = "stars"
        val itemsPerPage = "20"
        val page = 1

        val repositoryItemResponse = GitHubRepositoryData(
            id = 65932,
            name = "Test Repo",
            description = "Description Test Repo",
            stars = 12100,
            forks = 505,
            owner = GitHubOwner(
                id = 625,
                login = "TestUser",
                avatarUrl = "https://www.google.com/avatar"
            )
        )

        val repositoryResponse = GitHubResponse(
            totalCount = 658,
            incompleteResults = false,
            items = listOf(repositoryItemResponse)
        )

        every { api.getRepositories(query, sort, itemsPerPage, page) } returns
                Single.just(repositoryResponse)

        val result = repository.getRepositories(query, sort, itemsPerPage, page).blockingGet()

        verify { api.getRepositories(query, sort, itemsPerPage, page) }
        assertFalse(result.isEmpty())
    }

    @Test
    fun `getPullRequests - Empty List`() {
        val owner = "Test User"
        val repo = "Test Repo"
        every { api.getPullRequests(owner, repo) } returns Single.just(emptyList())

        val result = repository.getPullRequests(owner, repo).blockingGet()

        verify { api.getPullRequests(owner, repo) }
        assertTrue(result.isEmpty())
    }
}