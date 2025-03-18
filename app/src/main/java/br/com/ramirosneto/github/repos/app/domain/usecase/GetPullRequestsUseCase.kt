package br.com.ramirosneto.github.repos.app.domain.usecase

import br.com.ramirosneto.github.repos.app.data.remote.repository.GitHubRepository
import br.com.ramirosneto.github.repos.app.presentation.model.PullRequestDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPullRequestsUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    operator fun invoke(owner: String, repo: String): Single<List<PullRequestDTO>> {
        if (owner.isEmpty() || repo.isEmpty())
            throw Exception("Autor or repositório não podem ser vazios!")

        return try {
            repository.getPullRequests(owner, repo)
        } catch (e: HttpException) {
            Single.error(Exception("Error: ${e.code()}"))
        } catch (e: IOException) {
            Single.error(Exception(Exception("Error: Connection lost")))
        } catch (e: Exception) {
            Single.error(Exception(Throwable(e)))
        }
    }
}
