package br.com.ramirosneto.github.repos.app.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubPullRequest(
    val id: Int,
    val name: String?
)
