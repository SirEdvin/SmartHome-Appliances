@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("site.siredvin.vanilla")
    id("site.siredvin.publishing")
}

val modVersion: String by extra
val minecraftVersion: String by extra
val modBaseName: String by extra

baseShaking {
    projectPart.set("common")
    integrationRepositories.set(true)
    shake()
}

vanillaShaking {
    accessWideners.add("src/main/resources/smarthome_appliances-common.accesswidener")
    accessWideners.add("src/main/resources/smarthome_appliances.accesswidener")
    shake()
}

dependencies {
    implementation(libs.bundles.kotlin)
//    implementation(libs.bundles.cccommon)
    implementation(libs.bundles.common)
    api(libs.bundles.apicommon)
}

publishingShaking {
    shake()
}
