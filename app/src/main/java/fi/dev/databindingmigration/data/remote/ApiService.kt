package fi.dev.databindingmigration.data.remote

import fi.dev.databindingmigration.data.model.PostResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse>>
}