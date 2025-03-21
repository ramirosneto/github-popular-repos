package br.com.ramirosneto.github.repos.app.presentation.model

data class RepositoryDTO(
    val id: Long,
    val name: String?,
    val description: String?,
    val forksCount: Int,
    val starsCount: Int,
    val owner: RepositoryOwner
)
