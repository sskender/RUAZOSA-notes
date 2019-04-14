package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_note_details.*
import java.util.*

class NoteDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        var thisNoteHasBeenEdited = false

        var oldTitle: String? = null
        var oldDetails: String? = null
        var oldTimestamp: String? = null

        if (intent.extras != null) {
            oldTitle = intent.extras?.get("oldTitle").toString()
            oldDetails = intent.extras?.get("oldDetails").toString()
            oldTimestamp = intent.extras?.get("oldTimestamp").toString()

            noteTitleEditText.setText(oldTitle)
            noteDetailsEditText.setText(oldDetails)

            thisNoteHasBeenEdited = true
        }

        saveNoteButton.setOnClickListener {
            val resultIntent = Intent()

            resultIntent.putExtra("newTitle", noteTitleEditText.text.toString())
            resultIntent.putExtra("newDetails", noteDetailsEditText.text.toString())
            resultIntent.putExtra("newTimestamp", Date().toString())

            if (thisNoteHasBeenEdited) {
                resultIntent.putExtra("oldTitle", oldTitle)
                resultIntent.putExtra("oldDetails", oldDetails)
                resultIntent.putExtra("oldTimestamp", oldTimestamp)

                setResult(2, resultIntent)
                finish()
            } else {
                setResult(1, resultIntent)
                finish()
            }
        }

        cancelNoteButton.setOnClickListener {
            setResult(0, null)
            finish()
        }

    }

}
