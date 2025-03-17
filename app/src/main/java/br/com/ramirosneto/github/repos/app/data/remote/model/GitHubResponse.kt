package br.com.ramirosneto.github.repos.app.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubResponse(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    val items: List<GitHubRepositoryData>
)

@JsonClass(generateAdapter = true)
data class GitHubRepositoryData(
    val id: Int,
    val name: String?,
    val description: String?,
    @Json(name = "forks_count") val forksCount: Int,
    val watchers: Int,
    val score: Double
)
