package com.example.notes.viewholders

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.notes.NoteDetailsActivity
import com.example.notes.R
import com.example.notes.model.Note

/**
 * Fills data in note_item_in_list.xml
 *
 * Holds widgets in memory for each entry in NotesList model
 */
class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var noteTitleTextView: TextView? = null
    var noteTimestampTextView: TextView? = null
    var noteDeleteButton: ImageButton? = null

    var note: Note? = null

    init {
        noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView)
        noteTimestampTextView = itemView.findViewById(R.id.noteTimestampTextView)
        noteDeleteButton = itemView.findViewById(R.id.deleteNoteImageButton)

        itemView.setOnClickListener {
            val editNoteDetailsIntent = Intent(itemView.context, NoteDetailsActivity::class.java)

            editNoteDetailsIntent.putExtra("uuid", note?.uuid)
            editNoteDetailsIntent.putExtra("title", note?.noteTitle)
            editNoteDetailsIntent.putExtra("details", note?.noteDetails)

            (itemView.context as Activity).startActivityForResult(editNoteDetailsIntent, 0)
        }
    }

    fun bindNote(note: Note) {
        this.note = note
    }

}
