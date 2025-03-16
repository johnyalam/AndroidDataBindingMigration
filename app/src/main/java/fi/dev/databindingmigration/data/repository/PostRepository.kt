package fi.dev.databindingmigration.data.repository

import fi.dev.databindingmigration.data.remote.ApiService
import fi.dev.databindingmigration.data.model.PostResponse

class PostRepository(private val apiService: ApiService) : PostRepositoryImpl{
    override suspend fun getPosts(): List<PostResponse> {
        val response = apiService.getPosts()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("API Error: ${response.message()}")
        }
    }
}