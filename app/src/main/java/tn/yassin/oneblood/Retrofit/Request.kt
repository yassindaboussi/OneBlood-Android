package tn.yassin.oneblood.Retrofit

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Request {
    // A suspending function is simply a function that can be paused and resumed at a later time. They can execute a long running operation and wait for it to complete without blocking.
    @POST("signin")
    suspend fun Login(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("signin")
    suspend fun Login2(@Body User: User): Response<Loginresponse>

    @POST("signup")
    suspend fun Signup(@Body requestBody: RequestBody): Response<ResponseBody>
    @GET("profil")
    fun fetchPosts(@Header("Authorization") token: String): Call<Userresponse>
}