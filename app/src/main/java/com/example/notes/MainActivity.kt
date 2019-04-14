package com.example.notes

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.notes.adapters.NotesAdapter
import com.example.notes.dao.AppDatabase
import com.example.notes.dao.NotesList
import com.example.notes.model.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Room db
    private lateinit var db: AppDatabase

    // notes adapter
    private lateinit var notesAdapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // room db
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "notes-db"
        ).build()

        // recycle view and adapter
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter(db)
        notesRecyclerView.adapter = notesAdapter

        // grab notes from db
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

        if (resultCode >= 1) {
            val newNote = Note(
                data?.extras?.get("newTitle").toString(),
                data?.extras?.get("newDetails").toString(),
                data?.extras?.get("newTimestamp").toString()
            )

            NotesList.notesList.add(newNote)
            Thread(Runnable {
                db.noteDao().insert(newNote)
            }).start()
        }

        if (resultCode == 2) {
            val oldNote = Note(
                data?.extras?.get("oldTitle").toString(),
                data?.extras?.get("oldDetails").toString(),
                data?.extras?.get("oldTimestamp").toString()
            )

            Thread(Runnable {
                db.noteDao().deleteByProperties(oldNote.noteTitle, oldNote.noteDetails, oldNote.noteTimestamp)
                NotesList.notesList = db.noteDao().getAll() as MutableList<Note>
            }).start()
        }

        notesAdapter.notifyDataSetChanged()

    }

}
