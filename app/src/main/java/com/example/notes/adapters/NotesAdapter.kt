package com.example.notes.adapters

import android.app.Activity
import android.arch.persistence.room.Room
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
import com.example.notes.model.Note
import com.example.notes.model.NotesList

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private lateinit var db: AppDatabase

    /**
     * Overrode RecyclerView methods,
     * this will always work like this.
     *
     * Feel free to copy in any other projects.
     */
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NotesAdapter.NoteViewHolder {
        val context = p0.context
        val inflater = LayoutInflater.from(context)
        val notesListElement = inflater.inflate(R.layout.note_item_in_list, p0, false)

        // db
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "notes-db"
        ).build()

        return NoteViewHolder(notesListElement)
    }

    override fun getItemCount(): Int {
        return NotesList.notesList.size
    }

    override fun onBindViewHolder(p0: NoteViewHolder, p1: Int) {

        p0.bindNote(NotesList.notesList[p1])

        // display note properties
        p0.noteTitleTextView?.text = NotesList.notesList[p1].noteTitle
        p0.noteTimestampTextView?.text =
            NotesList.notesList[p1].noteTimestamp //p0.timestampFormat.format(NotesList.notesList[p1].noteTimestamp).toString()

        // delete note option
        p0.noteDeleteButton?.setOnClickListener {
            Thread(Runnable {
                db.noteDao().deleteByUuid(NotesList.notesList[p1].uuid)
            }).start()
            NotesList.notesList.removeAt(p1)
            this.notifyDataSetChanged()
        }
    }
    /*
     * End of overrode methods.
     */


    /**
     * Note inner class.
     *
     * On main screen shows each note in one line.
     *
     * Note contains title, timestamp and delete button (image)
     * Each note is clickable
     */
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // note timestamp format
        //val timestampFormat = SimpleDateFormat("dd/MM/yy HH:mm")

        // note_item_in_list properties
        var noteTitleTextView: TextView? = null
        var noteTimestampTextView: TextView? = null
        var noteDeleteButton: ImageButton? = null

        // one note item in RecyclerView list
        private var note: Note? = null

        init {
            // find note_item_in_list properties
            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView)
            noteTimestampTextView = itemView.findViewById(R.id.noteTimestampTextView)
            noteDeleteButton = itemView.findViewById(R.id.deleteNoteImageButton)

            // note is clickable
            itemView.setOnClickListener {
                // create intent to edit note
                val editNoteDetailsIntent = Intent(itemView.context, NoteDetailsActivity::class.java)

                // save note properties from current note to intent
                editNoteDetailsIntent.putExtra("uuid", note?.uuid)
                editNoteDetailsIntent.putExtra("title", note?.noteTitle)
                editNoteDetailsIntent.putExtra("details", note?.noteDetails)

                // start activity for note editing
                (itemView.context as Activity).startActivityForResult(editNoteDetailsIntent, 0)
            }
        }

        /**
         * Binds note from database,
         * so it is known which note is currently being worked on.
         */
        fun bindNote(note: Note) {
            this.note = note
        }

    }

}
