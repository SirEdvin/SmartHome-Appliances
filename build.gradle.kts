import java.util.function.BiConsumer

plugins {
    java
    id("site.siredvin.root") version "0.8.14"
    id("site.siredvin.release") version "0.8.14"
    id("com.dorongold.task-tree") version "2.1.1"
}

subprojectShaking {
    withKotlin.set(true)
}

val setupSubproject = subprojectShaking::setupSubproject

subprojects {
    setupSubproject(this)
}

githubShaking {
    modBranch.set("1.20")
    projectRepo.set("Template")
//    mastodonProjectName.set("Template")
    shake()
}

repositories {
    mavenCentral()
}