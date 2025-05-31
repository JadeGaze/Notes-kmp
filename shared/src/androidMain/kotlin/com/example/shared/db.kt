package com.example.shared

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shared.core.database.AppDatabase
import com.example.shared.core.database.dao.FoldersDao
import com.example.shared.core.database.dao.NotesDao
import com.example.shared.core.database.dao.UserDao
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("note_app.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

fun provideFoldersDao(db: AppDatabase): FoldersDao = db.foldersDao()
fun provideNotesDao(db: AppDatabase): NotesDao = db.notesDao()
fun provideUserDao(db: AppDatabase): UserDao = db.usersDao()

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
): AppDatabase {
    return builder
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

fun provideDb(application: Application): AppDatabase =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "note_app"
    ).build()

val dbModule = module {
    single { getDatabaseBuilder(get()) }
    single { getRoomDatabase(get()) }
//    single { provideDb(get()) }
    single { provideUserDao(get()) }
    single { provideNotesDao(get()) }
    single { provideFoldersDao(get()) }
}