package com.example.shared

import androidx.room.RoomDatabase
import com.example.shared.core.database.AppDatabase
import com.example.shared.core.database.dao.FoldersDao
import com.example.shared.core.database.dao.NotesDao
import com.example.shared.core.database.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

//fun getRoomDatabase(
//    builder: RoomDatabase.Builder<AppDatabase>
//): AppDatabase {
//    return builder
//        .setQueryCoroutineContext(Dispatchers.IO)
//        .build()
//}
//
//fun provideFoldersDao(db: AppDatabase): FoldersDao = db.foldersDao()
//fun provideNotesDao(db: AppDatabase): NotesDao = db.notesDao()
//fun provideUserDao(db: AppDatabase): UserDao = db.usersDao()

//fun provideDb(application: Application): AppDatabase =
//    Room.databaseBuilder(
//        application,
//        AppDatabase::class.java,
//        "note_app"
//    ).build()

//val dbModule = module {
////    single { getDatabaseBuilder(get()) }
//    single { getRoomDatabase(get()) }
////    single { provideDb(get()) }
//    single { provideUserDao(get()) }
//    single { provideNotesDao(get()) }
//    single { provideFoldersDao(get()) }
//}