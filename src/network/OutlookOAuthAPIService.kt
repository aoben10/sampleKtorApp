package com.theobencode.network

import network.models.Employee
import oauth.models.responses.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface OutlookOAuthAPIService {

    @FormUrlEncoded
    @POST("common/oauth2/v2.0/token")
    suspend fun getAccessTokenFromAuthCode(
        @Field("client_id") clientId: String = "",
        @Field("client_secret") clientSecret: String = "",
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("code") code: String,
        @Field("redirect_uri") redirectUrl: String = "http://localhost:8010/authorize"
    ): TokenResponse

    @FormUrlEncoded
    @POST("common/oauth2/v2.0/token")
    suspend fun getAccessTokenFromRefreshToken(
        @Field("client_id") clientId: String = "",
        @Field("client_secret") clientSecret: String = "",
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("refresh_token") code: String,
        @Field("redirect_uri") redirectUrl: String = "http://localhost:8010/authorize"
    ): TokenResponse
}

interface DummyAPIService {
    @GET("employees")
    suspend fun getMerchantSettings(): List<Employee>
}
