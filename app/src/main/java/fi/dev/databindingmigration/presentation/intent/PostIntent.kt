package fi.dev.databindingmigration.presentation.intent

sealed class PostIntent {
    data object LoadPosts : PostIntent()
}