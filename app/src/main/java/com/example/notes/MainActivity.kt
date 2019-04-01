package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.notes.adapters.NotesAdapter
import com.example.notes.model.Note
import com.example.notes.model.NotesList
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    // notes adapter
    private lateinit var notesAdapter: NotesAdapter

    private fun setupRecyclerViewAndAdapter() {
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter()
        notesRecyclerView.adapter = notesAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerViewAndAdapter()

        createNewNoteFloatingActionButton.setOnClickListener {
            val startNoteDetailsActivityIntent = Intent(this, NoteDetailsActivity::class.java)
            startActivityForResult(startNoteDetailsActivityIntent, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // nothing has been made (read from result code)
        if (resultCode == 0) {
            return
        }

        // something has changed
        // get new note properties and save them to database
        if (data != null && data.extras != null) {
            val note = Note()

            note.uuid = data.extras?.get("uuid") as UUID?
            note.noteTitle = data.extras?.get("title").toString()
            note.noteDetails = data.extras?.get("details").toString()
            note.noteTimestamp = data.extras?.get("timestamp") as Date?

            updateDatabase(note)
        }

        notesAdapter.notifyDataSetChanged()
    }

    private fun updateDatabase(note: Note) {
        // remove old note from database (find by uuid)
        for ((index, value) in NotesList.notesList.withIndex()) {
            if (value.uuid == note.uuid) {
                NotesList.notesList.removeAt(index)
                break
            }
        }
        // save new note
        NotesList.notesList.add(note)
    }

}
