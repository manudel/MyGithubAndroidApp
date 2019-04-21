package com.ed.mygithub.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface GitHubService {

    // Basic auth call to the root of the API will tell if the entered credentials are valid or not
    @GET("/")
    fun login(): Single<ResponseBody>

    @GET("/repositories")
    fun getRepositories(): Single<ResponseBody>

    companion object {
        fun create(): GitHubService {
            return ServiceGenerator.createService(GitHubService::class.java)
        }
    }
}