package com.example.shared.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.example.shared.core.database.dao.FoldersDao
import com.example.shared.core.database.dao.NotesDao
import com.example.shared.core.database.dao.UserDao
import com.example.shared.core.database.entity.FolderEntity
import com.example.shared.core.database.entity.NoteEntity
import com.example.shared.core.database.entity.UserEntity

@Database(
    version = 1,
    entities = [FolderEntity::class, NoteEntity::class, UserEntity::class]
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foldersDao(): FoldersDao
    abstract fun notesDao(): NotesDao
    abstract fun usersDao(): UserDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
