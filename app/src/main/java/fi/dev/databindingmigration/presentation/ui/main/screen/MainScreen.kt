package fi.dev.databindingmigration.presentation.ui.main.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.dev.databindingmigration.presentation.intent.PostIntent
import fi.dev.databindingmigration.presentation.state.PostState
import fi.dev.databindingmigration.presentation.ui.main.MainViewModel
import fi.dev.databindingmigration.presentation.ui.main.component.PostItem

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val postState by viewModel.postState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(PostIntent.LoadPosts)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (postState) {
            is PostState.Loading -> CircularProgressIndicator()
            is PostState.Success -> {
                val posts = (postState as PostState.Success).posts
                LazyColumn {
                    items(posts, key = { it.id }) { post ->
                        PostItem(post = post)
                    }
                }
            }
            is PostState.Error -> Text("Error: ${(postState as PostState.Error).message}")
        }
    }
}
