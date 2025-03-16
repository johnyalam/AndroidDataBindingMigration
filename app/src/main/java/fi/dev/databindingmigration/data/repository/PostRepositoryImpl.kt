package fi.dev.databindingmigration.data.repository

import fi.dev.databindingmigration.data.model.PostResponse

interface PostRepositoryImpl {
    suspend fun getPosts(): List<PostResponse>
}