package omkar.android.projects.app.expectuals

import io.ktor.client.HttpClient

expect class HttpClientEngine() {
    fun create(): HttpClient
}