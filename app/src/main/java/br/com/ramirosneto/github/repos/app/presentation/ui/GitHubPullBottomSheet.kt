package br.com.ramirosneto.github.repos.app.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import br.com.ramirosneto.github.repos.app.presentation.model.PullRequestDTO
import br.com.ramirosneto.github.repos.app.presentation.viewmodel.GitHubRepositoriesViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    owner: String,
    repo: String,
    viewModel: GitHubRepositoriesViewModel,
    onDismissRequest: () -> Unit
) {
    viewModel.loadPullRequests(owner, repo)

    val context = LocalContext.current
    val stateList = rememberLazyListState()
    val errorState = viewModel.repoErrorState.subscribeAsState("")
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var pullRequestsState = viewModel.pullRequests.subscribeAsState(emptyList())

    ModalBottomSheet(
        modifier = Modifier.testTag("PullRequestBottomSheet"),
        sheetState = bottomSheetState,
        onDismissRequest = {
            viewModel.clearPullRequests()
            onDismissRequest.invoke()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            if (errorState.value.isNotEmpty()) {
                PullRequestErrorScreen(errorState.value) {
                    viewModel.loadPullRequests(owner, repo)
                }
            } else if (pullRequestsState.value.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 48.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp),
                    state = stateList
                ) {
                    items(count = pullRequestsState.value.size) { index ->
                        val pullRequest = pullRequestsState.value[index]
                        PullRequestItem(pullRequest) { url ->
                            openWebPage(context, url)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PullRequestItem(pullRequestDTO: PullRequestDTO, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("PullRequestItem")
            .clickable {
                pullRequestDTO.url?.let { onClick(it) }
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
                    text = pullRequestDTO.title.orEmpty(),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = pullRequestDTO.description.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    model = pullRequestDTO.owner.avatarUrl,
                    contentDescription = "Repository Owner Avatar",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(64.dp))
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = pullRequestDTO.owner.login,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PullRequestErrorScreen(message: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
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

fun openWebPage(context: Context, url: String) {
    val webpage: Uri = url.toUri()
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    context.startActivity(intent)
}
