package com.example.notes.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.notes.R

/**
 * Fills data in note_item_in_list.xml
 *
 * Holds widgets in memory for each entry in NotesList model
 */
class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var noteTitleTextView: TextView? = null
    var noteTimestampTextView: TextView? = null
    var noteDeleteButton: ImageButton? = null

    init {
        noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView)
        noteTimestampTextView = itemView.findViewById(R.id.noteTimestampTextView)
        noteDeleteButton = itemView.findViewById(R.id.deleteNoteImageButton)
    }

}
