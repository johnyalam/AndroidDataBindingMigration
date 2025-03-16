package fi.dev.databindingmigration.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.dev.databindingmigration.data.repository.PostRepository
import fi.dev.databindingmigration.presentation.intent.PostIntent
import fi.dev.databindingmigration.presentation.state.PostState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository) : ViewModel() {

    private val _postState = MutableStateFlow<PostState>(PostState.Loading)
    val postState: StateFlow<PostState> = _postState.asStateFlow()

    fun handleIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.LoadPosts -> fetchPosts()
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _postState.value = PostState.Loading
            try {
                val posts = repository.getPosts()
                _postState.value = PostState.Success(posts)
            } catch (e: Exception) {
                _postState.value = PostState.Error(e.message ?: "Unknown error")
            }
        }
    }
}