package com.example.notes.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.notes.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    fun insert(note: Note)

    @Insert
    fun insertAll(vararg notes: Note)

    @Query("SELECT * FROM notesData")
    fun getAll(): List<Note>

    @Query("DELETE FROM notesData WHERE title = :title AND details = :details AND timestamp = :timestamp")
    fun deleteByProperties(title: String?, details: String?, timestamp: String?)

    @Delete
    fun delete(note: Note)

}
