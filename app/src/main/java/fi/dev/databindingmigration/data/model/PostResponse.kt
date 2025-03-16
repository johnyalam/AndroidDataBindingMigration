package fi.dev.databindingmigration.data.model

data class PostResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)