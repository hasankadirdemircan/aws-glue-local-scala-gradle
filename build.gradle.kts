plugins {
    scala
    application
    id("com.github.johnrengelman.shadow") version "7.1.1"
    `maven-publish`
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://aws-glue-etl-artifacts.s3.amazonaws.com/release/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.scala-lang:scala-library:2.12.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.tngtech.java:junit-dataprovider:1.13.1")
    testImplementation("org.mockito:mockito-inline:4.5.1")

    implementation("org.apache.spark:spark-core_2.11:2.4.3")
    implementation("org.apache.spark:spark-sql_2.11:2.4.3")

    //for amazon glue etl jobs
    implementation("com.amazonaws:AWSGlueETL:3.0.0")
    implementation("software.amazon.awssdk:aws-sdk-java:2.13.0")

    implementation ("com.lihaoyi:requests_2.12:0.7.0") //gradle
    // https://mvnrepository.com/artifact/jp.co.bizreach/aws-dynamodb-scala
    implementation("jp.co.bizreach:aws-dynamodb-scala_2.12:0.0.9")

// https://mvnrepository.com/artifact/com.auth0/auth0
    implementation("com.auth0:auth0:1.42.0")



}


application {
    mainClass.set("Connection")
}

//kotlin DSL
configurations.all {
    resolutionStrategy {
        force("org.antlr:antlr4-runtime:4.7.2")
        force("org.antlr:antlr4-tool:4.7.2")
    }
}
tasks {
    shadowJar {
        isZip64 = true
        from(sourceSets["main"].output)
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
    }

}
