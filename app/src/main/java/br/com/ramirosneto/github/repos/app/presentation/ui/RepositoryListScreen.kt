package br.com.ramirosneto.github.repos.app.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.ramirosneto.github.repos.app.data.remote.model.Repository
import br.com.ramirosneto.github.repos.app.presentation.viewmodel.RepositoryViewModel

@Composable
fun RepositoryListScreen(viewModel: RepositoryViewModel = hiltViewModel()) {
    val repositories = viewModel.getRepositories().collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(repositories) { repository ->
            repository?.let {
                RepositoryItem(repository)
            }
        }
    }
}

@Composable
fun RepositoryItem(repository: Repository) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text(text = repository.name, style = MaterialTheme.typography.h6)
        Text(
            text = repository.description ?: "No description",
            style = MaterialTheme.typography.body2
        )
        Text(text = "Forks: ${repository.forks_count}", style = MaterialTheme.typography.body2)
        Text(text = "Score: ${repository.score}", style = MaterialTheme.typography.body2)
    }
}
