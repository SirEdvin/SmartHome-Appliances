package site.siredvin.template.common.configuration

import net.minecraftforge.common.ForgeConfigSpec

object ModConfig {

    val enableSomething: Boolean
        get() = ConfigHolder.commonConfig.enableSomething.get()

    class CommonConfig internal constructor(builder: ForgeConfigSpec.Builder) {

        // Generic plugins
        var enableSomething: ForgeConfigSpec.BooleanValue

        init {
            builder.push("base")
            enableSomething = builder.comment("Something enabled")
                .define("enableSomething", true)
            builder.pop()
        }
    }
}
