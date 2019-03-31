package com.example.notes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.notes.model.Note
import com.example.notes.model.NotesList
import kotlinx.android.synthetic.main.activity_note_details.*

class NoteDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        // save button action
        saveNoteButton.setOnClickListener {
            val note = Note()

            note.noteTitle = noteTitleEditText.text.toString()
            note.noteDetails = noteDetailsEditText.text.toString()

            NotesList.notesList.add(note)

            finish()
        }
    }

}
