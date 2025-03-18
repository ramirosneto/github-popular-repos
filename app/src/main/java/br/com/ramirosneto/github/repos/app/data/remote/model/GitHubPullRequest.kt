package br.com.ramirosneto.github.repos.app.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubPullRequest(
    val title: String?,
    val body: String?,
    @Json(name = "html_url") val url: String?,
    @Json(name = "created_at") val createdAt: String?,
    val user: GitHubOwner
)
