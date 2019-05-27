import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val retrofit_version: String by project
val okHttp_version: String by project
val moshi = "1.8.0"

plugins {
    application
    kotlin("jvm") version "1.3.31"
    kotlin("kapt") version "1.3.31"
    id("com.github.ben-manes.versions") version "0.21.0"
}

group = "sampe"
version = "0.0.1-dev"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven("https://kotlin.bintray.com/ktor")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-locations:$ktor_version")

    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")

    implementation("com.squareup.okhttp3:okhttp:$okHttp_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttp_version")

    implementation("com.squareup.moshi:moshi-kotlin:$moshi")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")

    compile("org.koin:koin-ktor:2.0.0-rc-3")
    compile("org.koin:koin-logger-slf4j:2.0.0-rc-3")

    compile("io.ktor:ktor-gson:$ktor_version")
    testCompile("io.ktor:ktor-server-tests:$ktor_version")
}


tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs += "-Xuse-experimental=io.ktor.locations.KtorExperimentalLocationsAPI"
        jvmTarget = "1.8"
    }
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
