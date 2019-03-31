package com.example.notes.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.notes.R
import com.example.notes.model.NotesList

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.ViewHolder> () {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val context = p0.context
        val inflater = LayoutInflater.from(context)
        val notesListElement = inflater.inflate(R.layout.note_item_in_list, p0, false)
        return ViewHolder(notesListElement)
    }

    override fun getItemCount(): Int {
        return NotesList.notesList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.noteTitleTextView?.text = NotesList.notesList[p1].noteTitle

        // TODO onclick listener to edit
    }

    /**
     * Fills data in note_item_in_list.xml
     *
     * Holds widgets in memory for each entry in NotesList model
     */
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var noteTitleTextView: TextView? = null

        init {
            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView)
        }
    }

}
