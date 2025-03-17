package br.com.ramirosneto.github.repos.app.presentation.model

data class RepositoryDTO(
    val id: Int,
    val name: String?,
    val description: String?,
    val forksCount: Int,
    val watchers: Int,
    val score: Double
)
