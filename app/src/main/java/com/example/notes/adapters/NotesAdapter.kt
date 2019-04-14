package com.example.notes.adapters

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.notes.NoteDetailsActivity
import com.example.notes.R
import com.example.notes.dao.AppDatabase
import com.example.notes.dao.NotesList
import com.example.notes.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    // only for view layout, nothing to do with database format
    private val noteTimestampFormat = SimpleDateFormat("dd/MM/yy HH:mm", Locale.US)

    // database connection
    private lateinit var db: AppDatabase


    constructor(db: AppDatabase) : this() {
        this.db = db
    }


    /**
     * RecyclerView boilerplate
     */
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NotesAdapter.NoteViewHolder {
        val context = p0.context
        val inflater = LayoutInflater.from(context)
        val notesListElement = inflater.inflate(R.layout.note_item_in_list, p0, false)

        return NoteViewHolder(notesListElement)
    }


    override fun getItemCount(): Int {
        return NotesList.notesList.size
    }


    override fun onBindViewHolder(p0: NoteViewHolder, p1: Int) {
        val currentNote: Note = NotesList.notesList[p1]

        // note properties (from inner class)
        p0.noteTitleTextView?.text = currentNote.noteTitle
        p0.noteTimestampTextView?.text = noteTimestampFormat.format(Date(currentNote.noteTimestamp)).toString()

        // edit note (intent)
        p0.itemView.setOnClickListener {
            //Toast.makeText(p0.itemView.context, "Position is " + p1, Toast.LENGTH_SHORT).show()
            val editNoteDetailsIntent = Intent(p0.itemView.context, NoteDetailsActivity::class.java)

            editNoteDetailsIntent.putExtra("oldTitle", currentNote.noteTitle)
            editNoteDetailsIntent.putExtra("oldDetails", currentNote.noteDetails)
            editNoteDetailsIntent.putExtra("oldTimestamp", currentNote.noteTimestamp)

            (p0.itemView.context as Activity).startActivityForResult(editNoteDetailsIntent, 0)
        }

        // delete note
        p0.noteDeleteButton?.setOnClickListener {
            //Toast.makeText(p0.itemView.context, "Delete is " + p1, Toast.LENGTH_SHORT).show()
            NotesList.notesList.removeAt(p1)
            this.notifyDataSetChanged()

            Thread(Runnable {
                db.noteDao()
                    .deleteByProperties(currentNote.noteTitle, currentNote.noteDetails, currentNote.noteTimestamp)
            }).start()
        }

    }


    /**
     * Note inner class.
     *
     * On main screen shows each note in one line.
     *
     * Note contains title, timestamp and delete button (image)
     * Each note is clickable
     */
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var noteTitleTextView: TextView? = null
        var noteTimestampTextView: TextView? = null
        var noteDeleteButton: ImageButton? = null

        init {
            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView)
            noteTimestampTextView = itemView.findViewById(R.id.noteTimestampTextView)
            noteDeleteButton = itemView.findViewById(R.id.deleteNoteImageButton)
        }

    }

}
