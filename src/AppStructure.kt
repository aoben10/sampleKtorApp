package com.theobencode

import com.theobencode.network.DummyAPIService
import com.theobencode.network.OutlookOAuthAPIService
import com.theobencode.oauth.AuthenticationHelper
import io.ktor.application.call
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.koin.ktor.ext.inject

fun Route.home() {
    val service by inject<DummyAPIService>()
    AuthenticationHelper.getLoginUrl()

    get("/") {
        val employees = service.getMerchantSettings()
        call.respond(employees)
    }

}

fun Route.authorize() {
    val outlookService by inject<OutlookOAuthAPIService>()

    post("/authorize") {
        val authCode = call.receiveParameters()["code"]!!
        val tokenResponse = outlookService.getAccessTokenFromAuthCode(code = authCode)
        val myToken = tokenResponse.accessToken
    }
}

fun Route.employees() {
    route("/employee") {
        get {
            call.respondText("Employee Pe")
        }
    }
}

