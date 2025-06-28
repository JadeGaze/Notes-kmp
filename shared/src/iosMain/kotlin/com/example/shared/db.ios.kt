package com.example.shared

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shared.core.database.AppDatabase
import com.example.shared.core.database.dao.FoldersDao
import com.example.shared.core.database.dao.NotesDao
import com.example.shared.core.database.dao.UserDao
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/my_room.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
    )
}

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

//fun provideFoldersDao(db: AppDatabase): FoldersDao = db.foldersDao()
//fun provideNotesDao(db: AppDatabase): NotesDao = db.notesDao()
//fun provideUserDao(db: AppDatabase): UserDao = db.usersDao()
//
//fun getRoomDatabase(
//    builder: RoomDatabase.Builder<AppDatabase>,
//    dispatcher: CoroutineDispatcher
//): AppDatabase {
//    return builder
//        .setQueryCoroutineContext(dispatcher)
//        .build()
//}

//fun provideDb(application: Application): AppDatabase =
//    Room.databaseBuilder(
//        application,
//        AppDatabase::class.java,
//        "note_app"
//    ).build()

val iosDbModule = module {
//    single<CoroutineDispatcher> { Dispatchers.IO }
    single { getDatabaseBuilder() }
//    single { getRoomDatabase(get(), get()) }
//    single { provideDb(get()) }
//    single { provideUserDao(get()) }
//    single { provideNotesDao(get()) }
//    single { provideFoldersDao(get()) }
//    single<FirebaseAuth> { Firebase.auth }
//    single<FirebaseFirestore> { Firebase.firestore }

}

//val dbModule = module {
////    single { getDatabaseBuilder(get()) }
//    single { getRoomDatabase(get()) }
////    single { provideDb(get()) }
//    single { provideUserDao(get()) }
//    single { provideNotesDao(get()) }
//    single { provideFoldersDao(get()) }
//}