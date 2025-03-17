package br.com.ramirosneto.github.repos.app.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import br.com.ramirosneto.github.repos.app.application.MyApplication
import br.com.ramirosneto.github.repos.app.presentation.theme.GithubpopularreposTheme
import br.com.ramirosneto.github.repos.app.presentation.ui.RepositoryListScreen
import br.com.ramirosneto.github.repos.app.presentation.viewmodel.GitHubRepositoriesViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: GitHubRepositoriesViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)

        setContent {
            GithubpopularreposTheme {
                RepositoryListScreen(viewModel = viewModel)
            }
        }
    }
}