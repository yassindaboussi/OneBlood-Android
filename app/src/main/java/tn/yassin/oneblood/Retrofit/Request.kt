package tn.yassin.oneblood.Retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import okhttp3.RequestBody

import retrofit2.http.*
import java.util.*
import retrofit2.http.GET





interface Request {
    // A suspending function is simply a function that can be paused and resumed at a later time. They can execute a long running operation and wait for it to complete without blocking.
    @POST("signin")
    suspend fun Login2(@Body User: Loginresponse): Response<Loginresponse>

    @POST("signup")
    suspend fun Signup(@Body Signup: Loginresponse): Response<Loginresponse>

    // UpdateUser Information
    @POST("UpdateUser")
    suspend fun UpdateUser(@Body User: Loginresponse): Response<Loginresponse>

    // UpdateUser Password
    @POST("UpdatePassword")
    suspend fun UpdatePassword(@Body User: Loginresponse): Response<Loginresponse>

    // UpdateUser Avatar
    @POST("UpdateAvatar")
    suspend fun UpdateAvatar(@Body User: Loginresponse): Response<Loginresponse>

    @POST("Addbesion")
    suspend fun AddNeedy(@Body User: Needyresponse): Response<Needyresponse>

    // SHOW All  POSTS
    @GET("GetAllBesoin")
    fun GetAllBesoin(): Call<List<Needyresponse>>

    // SHOW ONLY MY POSTS
    @GET("GetAllMyPost")
    fun GetAllMyPost(@Header("postedby") postedby: String): Call<List<Needyresponse>>

    // SHOW Situation Danger/Normal
    @GET("ShowSituation")
    fun ShowSituation(@Header("situation") situation: String): Call<List<Needyresponse>>

    @POST("UpdatePost")
    suspend fun UpdatePost(@Body User: Needyresponse): Response<Needyresponse>

    @HTTP(method = "DELETE", path = "deletPost", hasBody = true)
    suspend fun deletPost(@Body body: Needyresponse): Response<Needyresponse>

    // Close Casse (Needy)
    @POST("CloseCase")
    suspend fun CloseCase(@Body body: Needyresponse): Response<Needyresponse>

    @POST("UpdateAvatarPost")
    suspend fun UpdateAvatarPost(@Body body: Needyresponse): Response<Needyresponse>

}