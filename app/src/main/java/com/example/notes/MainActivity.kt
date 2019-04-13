package com.example.notes

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.notes.adapters.NotesAdapter
import com.example.notes.dao.AppDatabase
import com.example.notes.model.Note
import com.example.notes.model.NotesList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Room db
    private lateinit var db: AppDatabase

    // notes adapter
    private lateinit var notesAdapter: NotesAdapter

    /**
     * Setup Room database
     */
    private fun setupRoomDB() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "notes-db"
        ).build()
    }

    /**
     * Setup RecyclerView and Adapter on app start
     */
    private fun setupRecyclerViewAndAdapter() {
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter()
        notesRecyclerView.adapter = notesAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRoomDB()
        setupRecyclerViewAndAdapter()

        // load all notes on startup
        Thread(Runnable {
            NotesList.notesList = db.noteDao().getAll() as MutableList<Note>
            notesAdapter.notifyDataSetChanged()
        }).start()


        // new note button
        createNewNoteFloatingActionButton.setOnClickListener {
            val startNoteDetailsActivityIntent = Intent(this, NoteDetailsActivity::class.java)
            startActivityForResult(startNoteDetailsActivityIntent, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.notifyDataSetChanged()
    }

    /**
     * Call this method on one of following actions:
     *  - new note was created
     *  - existing note was updated
     *
     *  Read action from result code
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // nothing has been made
        if (resultCode == 0) {
            return
        }

        // something has changed
        // get new note properties and save them to database
        if (data != null && data.extras != null) {

            val note = Note(
                data.extras?.get("title").toString(),
                data.extras?.get("details").toString(),
                data.extras?.get("timestamp").toString()
            )

            Thread(Runnable {
                db.noteDao().insert(note)
                NotesList.notesList = db.noteDao().getAll() as MutableList<Note>
            }).start()
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
