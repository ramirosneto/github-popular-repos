package br.com.ramirosneto.github.repos.app.presentation.model

data class RepositoryDTO(
    val id: Int,
    val name: String?,
    val description: String?,
    val forksCount: Int,
    val starsCount: Int,
    val owner: RepositoryOwner
)

data class RepositoryOwner(
    val login: String,
    val avatarUrl: String
)
