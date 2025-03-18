package br.com.ramirosneto.github.repos.app.util

import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubOwner
import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubRepositoryData
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryOwner

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
