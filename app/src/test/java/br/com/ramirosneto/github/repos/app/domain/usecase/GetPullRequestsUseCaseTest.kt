package br.com.ramirosneto.github.repos.app.domain.usecase

import br.com.ramirosneto.github.repos.app.data.remote.repository.GitHubRepository
import br.com.ramirosneto.github.repos.app.presentation.model.PullRequestDTO
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryOwner
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GetPullRequestsUseCaseTest {

    private lateinit var gitHubRepository: GitHubRepository
    private lateinit var getPullRequestsUseCase: GetPullRequestsUseCase

    @Before
    fun setUp() {
        gitHubRepository = mockk()
        getPullRequestsUseCase = GetPullRequestsUseCase(gitHubRepository)
    }

    @Test
    fun `invoke should return a list of PullRequestDTO when repository returns success`() {
        val owner = "owner"
        val repo = "repo"
        val expectedPullRequests = listOf(
            PullRequestDTO(
                title = "title",
                description = "description",
                url = "https://www.github.com/repo/125",
                date = "2025-03-17T22:14:15Z",
                owner = RepositoryOwner(
                    login = "TestUser",
                    avatarUrl = "https://www.google.com/avatar"
                )
            )
        )
        every { gitHubRepository.getPullRequests(owner, repo) } returns
                Single.just(expectedPullRequests)

        val testObserver: TestObserver<List<PullRequestDTO>> =
            getPullRequestsUseCase(owner, repo).test()

        testObserver.assertNoErrors()
            .assertValue { it == expectedPullRequests }
            .assertComplete()

        verify { gitHubRepository.getPullRequests(owner, repo) }
    }

    @Test
    fun `invoke should not throw an Exception when owner and repo are not empty`() {
        val owner = "userGit"
        val repo = "Glide"

        every { gitHubRepository.getPullRequests(owner, repo) } returns Single.just(emptyList())

        getPullRequestsUseCase(owner, repo).test()
            .assertNoErrors()
            .assertComplete()
        verify { gitHubRepository.getPullRequests(owner, repo) }
    }

    @Test
    fun `invoke should return an Error when repository throws HttpException`() {
        val owner = "owner"
        val repo = "repo"
        val httpException = HttpException(Response.error<Any>(404, ResponseBody.create(null, "")))
        every { gitHubRepository.getPullRequests(owner, repo) } returns
                Single.error(httpException)

        val testObserver: TestObserver<List<PullRequestDTO>> = getPullRequestsUseCase(owner, repo).test()
        testObserver.assertError(httpException)
        verify { gitHubRepository.getPullRequests(owner, repo) }
    }

    @Test
    fun `invoke should return an Error when repository throws IOException`() {
        val owner = "owner"
        val repo = "repo"
        val ioException = IOException()
        every { gitHubRepository.getPullRequests(owner, repo) } returns
                Single.error(ioException)

        val testObserver: TestObserver<List<PullRequestDTO>> =
            getPullRequestsUseCase(owner, repo).test()

        testObserver.assertError(ioException)

        verify { gitHubRepository.getPullRequests(owner, repo) }
    }

    @Test
    fun `invoke should return an Error when repository throws generic Exception`() {
        val owner = "owner"
        val repo = "repo"
        val exception = Exception("Generic Error")
        every { gitHubRepository.getPullRequests(owner, repo) } returns Single.error(exception)

        val testObserver: TestObserver<List<PullRequestDTO>> =
            getPullRequestsUseCase(owner, repo).test()

        testObserver
            .assertError(exception)
            .assertNotComplete()

        verify { gitHubRepository.getPullRequests(owner, repo) }
    }
}
