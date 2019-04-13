package com.example.notes.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "notesData")
data class Note(
    @ColumnInfo(name = "title") var noteTitle: String?,
    @ColumnInfo(name = "details") var noteDetails: String?,
    @ColumnInfo(name = "timestamp") var noteTimestamp: String?
) {

    @ColumnInfo(name = "uuid")
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

}