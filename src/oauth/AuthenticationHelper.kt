package com.theobencode.oauth

import io.ktor.auth.OAuthServerSettings
import io.ktor.http.HttpMethod
import okhttp3.HttpUrl

object AuthenticationHelper {
    const val googleOAuth = "google-oath"

    val googleOauthProvider = OAuthServerSettings.OAuth2ServerSettings(
        name = "google",
        authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
        accessTokenUrl = "https://www.googleapis.com/oauth2/v3/token",
        requestMethod = HttpMethod.Post,

        clientId = "xxxxxxxxxxx.apps.googleusercontent.com",
        clientSecret = "yyyyyyyyyyy",
        defaultScopes = listOf("profile") // no email, but gives full name, picture, and id
    )

    private val permissionScopes = listOf("openid", "Mail.ReadWrite", "User.Read")

    private fun getScopeString(): String {
        var scopeString = ""
        permissionScopes.forEach { scope ->
            scopeString += "$scope "
        }
        return scopeString.trim()
    }

    fun getLoginUrl(): String {
        return HttpUrl.Builder()
            .scheme("https")
            .host("login.microsoftonline.com")
            .addPathSegments("common/oauth2/v2.0/authorize")
            .addQueryParameter("client_id", "*client id goes here*")
            .addQueryParameter("redirect_uri", "http://localhost:8080")
            .addQueryParameter("response_type", "code")
            .addQueryParameter("scope", getScopeString())
            .addQueryParameter("response_mode", "form_post")
            .build().toString()
    }
}