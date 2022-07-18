package com.task.shortlyapp.repository.remote

import com.task.shortlyapp.BuildConfig
import com.task.shortlyapp.repository.server.ShortlyAppAPI
import com.task.shortlyapp.utils.RetrofitClient
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.HttpException

class RetrofitClientTest {

    /** Build & get an instance of Retrofit */
    private val retrofit = RetrofitClient.getRetrofit(
        RetrofitClient.getHttpClient(),
        RetrofitClient.getGsonConverter()
    )

    /**
     * Test if BASE_URL is correct.
     */
    @Test
    fun testRetrofitInstance() {
        // Assert that, Retrofit's base url matches to our BASE_URL
        assert(retrofit.baseUrl().url().toString() == BuildConfig.BASE_URL)
    }

    /**
     * Test the success use case of shortening a link
     */
    @Test
    fun testShortenLinkServiceSuccess() {
        // Get an instance of ShortenAPI
        val shortenAppAPI = retrofit.create(
            ShortlyAppAPI::class.java
        )
        runBlocking {
            // Create a new request for our API calling
            val query = "https://google.com"
            // Execute the API call
            val response = shortenAppAPI.shortenUrl(query)
            // Check for success body
            assert(response.ok)
            assert(response.result != null)
        }
    }

    /**
     * Test the failure use case of shortening a link
     */
    @Test
    fun testShortenLinkServiceFailure() {
        // Get an instance of ShortenAPI
        val shortenAppAPI = retrofit.create(
            ShortlyAppAPI::class.java
        )
        runBlocking {
            // Create a new request for our API calling
            val query = ""
            // Execute the API call
            try {
                shortenAppAPI.shortenUrl(query)
            } catch (e: HttpException) {
                // Check for error body and code
                assert(e.code() == 400)
                assert(e.response()?.errorBody() != null)
            }
        }
    }
}
