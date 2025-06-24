package com.example.shared

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.shared.core.database.AppDatabase
import com.example.shared.core.database.dao.FoldersDao
import com.example.shared.core.database.dao.NotesDao
import com.example.shared.core.database.dao.UserDao
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import java.io.File

fun getDatabaseBuilder() : RoomDatabase.Builder<AppDatabase> {
    val dbFile = File("note_app.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
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

val dbModule = module {
    single { getDatabaseBuilder() }
    single { getRoomDatabase(get()) }
//    single { provideDb(get()) }
    single { provideUserDao(get()) }
    single { provideNotesDao(get()) }
    single { provideFoldersDao(get()) }
}
