package br.com.ramirosneto.github.repos.app.util

import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubOwner
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubPullRequest
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubRepositoryData
import br.com.ramirosneto.github.repos.app.presentation.model.PullRequestDTO
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryOwner
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun GitHubRepositoryData.toRepositoryDTO() = RepositoryDTO(
    id = id,
    name = name,
    description = description,
    forksCount = forks,
    starsCount = stars,
    owner = owner.toRepositoryOwner()
)

fun GitHubOwner.toRepositoryOwner() = RepositoryOwner(
    login = login,
    avatarUrl = avatarUrl
)

fun GitHubPullRequest.toPullRequestDTO() = PullRequestDTO(
    title = title,
    description = body,
    date = createdAt?.formatDate(),
    url = url,
    owner = user.toRepositoryOwner()
)

fun String.formatDate(): String {
    val inputFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.getDefault())

    return try {
        val dateTime = LocalDateTime.parse(this, inputFormatter)
        val zonedDateTime = ZonedDateTime.of(dateTime, ZoneId.of("UTC"))
        zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).format(outputFormatter)
    } catch (e: Exception) {
        e.message.orEmpty()
    }
}
