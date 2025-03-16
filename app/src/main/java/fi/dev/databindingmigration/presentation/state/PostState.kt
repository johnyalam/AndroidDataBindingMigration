package fi.dev.databindingmigration.presentation.state

import fi.dev.databindingmigration.data.model.PostResponse

sealed class PostState {
    object Loading : PostState()
    data class Success(val posts: List<PostResponse>) : PostState()
    data class Error(val message: String) : PostState()
}