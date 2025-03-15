package br.com.ramirosneto.github.repos.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.ramirosneto.github.repos.app.presentation.theme.GithubpopularreposTheme
import br.com.ramirosneto.github.repos.app.presentation.ui.RepositoryListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubpopularreposTheme {
                RepositoryListScreen()
            }
        }
    }
}