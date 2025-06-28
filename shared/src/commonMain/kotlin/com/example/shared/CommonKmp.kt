package com.example.shared

import com.example.shared.feature.auth.authModule
import com.example.shared.feature.auth.ui.SignInViewModel
import com.example.shared.feature.folders.foldersModule
import com.example.shared.feature.note.noteModule
import com.example.shared.feature.notes.notesModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

object CommonKmp {

    @Throws(Exception::class)
    fun getSignInViewModel(): SignInViewModel = getKoin().get()

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
                authModule,
                foldersModule,
                noteModule,
                notesModule,




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
