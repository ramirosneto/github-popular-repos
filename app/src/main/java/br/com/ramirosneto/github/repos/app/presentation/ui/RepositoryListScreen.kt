package br.com.ramirosneto.github.repos.app.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.ramirosneto.github.repos.app.data.remote.model.Repository
import br.com.ramirosneto.github.repos.app.presentation.viewmodel.GitHubRepositoriesViewModel

@Composable
fun RepositoryListScreen(viewModel: GitHubRepositoriesViewModel) {
    /*val repositories: LazyPagingItems<Repository> = viewModel.getRepositories().collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(repositories) { repository ->
            repository.name?.let {
                RepositoryItem(repository)
            }
        }
    }*/
}

@Composable
fun RepositoryItem(gitHubRepository: Repository) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = gitHubRepository.name!!, style = MaterialTheme.typography.bodySmall)
        Text(
            text = gitHubRepository.description ?: "No description",
            style = MaterialTheme.typography.bodySmall
        )
        Text(text = "Forks: ${gitHubRepository.forks}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Score: ${gitHubRepository.score}", style = MaterialTheme.typography.bodySmall)
    }
}
