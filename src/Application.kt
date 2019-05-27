package com.theobencode

import com.theobencode.di.applicationModule
import com.theobencode.di.networkModule
import com.theobencode.oauth.AuthenticationHelper.googleOAuth
import com.theobencode.oauth.AuthenticationHelper.googleOauthProvider
import com.theobencode.utils.HttpBinError
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.auth.Authentication
import io.ktor.auth.oauth
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.util.error
import org.koin.Logger.slf4jLogger
import org.koin.ktor.ext.Koin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.DateFormat


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    configureFeatures()
    registerRoutes()
}

fun Application.configureFeatures() {
    install(Koin) {
        slf4jLogger()
        modules(listOf(applicationModule, networkModule))
    }
    install(StatusPages) {
        exception<Throwable> { cause ->
            log.error(cause)
            val error = HttpBinError(HttpStatusCode.InternalServerError, call.request.local.uri, cause.toString(), cause.cause?.message)
            call.respond(HttpStatusCode.InternalServerError, error)
        }
    }


    install(Authentication) {
        oauth(name = googleOAuth) {
            providerLookup = { googleOauthProvider }
            urlProvider

        }
    }
    install(CallLogging)
    install(DefaultHeaders)
    install(Locations)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
            setDateFormat(DateFormat.LONG)
        }
    }

}


fun Application.registerRoutes() {
    routing {
        get("/customer") {
            val model = Customer(id = 1, name = "Mary Jacnnnkson", email = "mary@jane.com")
            call.respond(model)
        }

        home()
        authorize()
        employees()
    }
}

data class Customer(val id: Int = 1, val name: String, val email: String)

val logger: Logger = LoggerFactory.getLogger("")
