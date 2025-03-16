package br.com.ramirosneto.github.repos.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import br.com.ramirosneto.github.repos.app.presentation.theme.GithubpopularreposTheme
import br.com.ramirosneto.github.repos.app.presentation.ui.RepositoryListScreen
import br.com.ramirosneto.github.repos.app.presentation.viewmodel.GitHubRepositoriesViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GitHubRepositoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubpopularreposTheme {
                RepositoryListScreen(viewModel)
            }
        }
    }
}