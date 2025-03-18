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
    @Json(name = "stargazers_count") val stars: Int,
    val forks: Int,
    val owner: GitHubOwner
)

@JsonClass(generateAdapter = true)
data class GitHubOwner(
    val id: Int,
    val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
)
