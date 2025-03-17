package br.com.ramirosneto.github.repos.app.util

import br.com.ramirosneto.github.repos.app.data.remote.model.GitHubRepositoryData
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO

fun GitHubRepositoryData.toRepositoryDTO() = RepositoryDTO(
    id, name, description, forksCount, watchers, score
)
