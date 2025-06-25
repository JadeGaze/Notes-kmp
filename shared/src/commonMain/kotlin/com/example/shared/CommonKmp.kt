package com.example.shared

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

object CommonKmp {

    fun initKoin(
        configuration: Configuration,
        appDeclaration: KoinAppDeclaration = {},
    ) {
        startKoin {
            appDeclaration()
            modules(
                createConfiguration(configuration),
//                networkModule,
//                qualifierModule,

                // 2 варианта
//                storageModule,
                platformModule(),

//                featureModule,
            )
        }
    }

    private fun createConfiguration(configuration: Configuration) = module {
        single<Configuration> { configuration }
        single<PlatformConfiguration> { configuration.platformConfiguration }

//        single<Database> {
//            Database(get())
//        }
    }
}
