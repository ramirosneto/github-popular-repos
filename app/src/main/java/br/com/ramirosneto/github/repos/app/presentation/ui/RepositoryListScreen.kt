package br.com.ramirosneto.github.repos.app.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.ramirosneto.github.repos.app.R
import br.com.ramirosneto.github.repos.app.presentation.components.ExpandableText
import br.com.ramirosneto.github.repos.app.presentation.components.IconWithText
import br.com.ramirosneto.github.repos.app.presentation.model.RepositoryDTO
import br.com.ramirosneto.github.repos.app.presentation.viewmodel.GitHubRepositoriesViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListScreen(viewModel: GitHubRepositoriesViewModel) {
    val pagingData = viewModel.repositories.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val stateList = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.java_repos),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { pagingData.refresh() }) {
                        Icon(Icons.Filled.Refresh, "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            if (pagingData.itemCount > 0) {
                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        stateList.animateScrollToItem(index = 0)
                    }
                }) {
                    Icon(Icons.Filled.KeyboardArrowUp, "Back to Top")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (pagingData.loadState.refresh) {
                is LoadState.Error -> ErrorScreen(
                    message = (pagingData.loadState.refresh as LoadState.Error).error.message
                        ?: stringResource(R.string.unknown_error),
                    onClick = { pagingData.retry() }
                )

                is LoadState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp),
                        state = stateList
                    ) {
                        items(
                            count = pagingData.itemCount
                        ) { index ->
                            val repository = pagingData[index]
                            repository?.let {
                                RepositoryItem(viewModel, it)
                            }
                        }

                        if (pagingData.loadState.append is LoadState.Loading) {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RepositoryItem(viewModel: GitHubRepositoriesViewModel, gitHubRepository: RepositoryDTO) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("RepositoryItem")
            .clickable {
                showBottomSheet = true
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(3f),
            ) {
                Text(
                    text = gitHubRepository.name.orEmpty(),
                    style = MaterialTheme.typography.titleLarge
                )
                ExpandableText(
                    modifier = Modifier.padding(top = 8.dp),
                    text = gitHubRepository.description.orEmpty(),
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    IconWithText(
                        icon = Icons.Filled.Star,
                        text = gitHubRepository.starsCount.toString()
                    )
                    IconWithText(
                        icon = painterResource(id = R.drawable.fork_icon),
                        text = gitHubRepository.forksCount.toString()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    model = gitHubRepository.owner.avatarUrl,
                    contentDescription = "Repository Owner Avatar",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(64.dp))
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = gitHubRepository.owner.login,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    if (showBottomSheet) {
        BottomSheet(
            owner = gitHubRepository.owner.login,
            repo = gitHubRepository.name.orEmpty(),
            viewModel = viewModel
        ) {
            showBottomSheet = false
        }
    }
}

@Composable
fun ErrorScreen(message: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            text = message
        )
        IconButton(onClick = { onClick() }) {
            Icon(
                Icons.Filled.Refresh, "Refresh"
            )
        }
    }
}
