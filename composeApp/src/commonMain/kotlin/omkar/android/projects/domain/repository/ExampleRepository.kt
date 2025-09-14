package omkar.android.projects.domain.repository

import omkar.android.projects.data.model.ExampleResponse
import omkar.android.projects.app.utils.ApiResponseWrapper

interface ExampleRepository {
    suspend fun fetchExample(): ApiResponseWrapper<ExampleResponse>
}