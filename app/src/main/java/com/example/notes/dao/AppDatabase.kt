package com.example.notes.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.notes.model.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
