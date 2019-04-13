package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_note_details.*
import java.util.*

class NoteDetailsActivity : AppCompatActivity() {

    // change this flag depending on if a new note is created or the old one edited
    //private var thisIsANewNote = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        // fill from intent extra (if note is being edited)
        // else text fields will just remain empty
        if (intent.extras != null) {
            noteTitleEditText.setText(intent.extras?.get("title").toString())
            noteDetailsEditText.setText(intent.extras?.get("details").toString())
            //thisIsANewNote = false
        }

        // save button action
        saveNoteButton.setOnClickListener {
            //val note = Note(noteTitleEditText.text.toString(), noteDetailsEditText.text.toString(), Date())

            /*
            // grab values from text fields
            note.noteTitle = noteTitleEditText.text.toString()
            note.noteDetails = noteDetailsEditText.text.toString()
            note.noteTimestamp = Date()

            // create new random uuid for new note
            note.uuid = if (thisIsANewNote) UUID.randomUUID() else intent.extras?.get("uuid") as UUID
            */

            // fill result intent
            val resultIntent = Intent()
            //resultIntent.putExtra("uuid", note.uuid)
            resultIntent.putExtra("title", noteTitleEditText.text.toString())
            resultIntent.putExtra("details", noteDetailsEditText.text.toString())
            resultIntent.putExtra("timestamp", Date().toString())

            // send result code 1, note changed
            setResult(1, resultIntent)
            finish()
        }

        // cancel button action
        cancelNoteButton.setOnClickListener {
            // send result code zero, nothing new is done
            setResult(0, null)
            finish()
        }

    }

}
