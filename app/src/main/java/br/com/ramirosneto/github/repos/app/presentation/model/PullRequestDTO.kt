package br.com.ramirosneto.github.repos.app.presentation.model

data class PullRequestDTO(
    val title: String?,
    val description: String?,
    val url: String?,
    val date: String?,
    val owner: RepositoryOwner
)
