package br.com.ramirosneto.github.repos.app.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val repositories: List<Repository>
)

@JsonClass(generateAdapter = true)
data class Repository(
    val id: Int,
    val name: String?,
    val description: String?,
    val forks: Int,
    val watchers: Int,
    val score: Double,
)
