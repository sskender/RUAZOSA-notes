package com.example.notes.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.notes.R
import com.example.notes.model.NotesList
import com.example.notes.viewholders.NoteViewHolder
import java.text.SimpleDateFormat

class NotesAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NoteViewHolder {
        val context = p0.context
        val inflater = LayoutInflater.from(context)
        val notesListElement = inflater.inflate(R.layout.note_item_in_list, p0, false)
        return NoteViewHolder(notesListElement)
    }

    override fun getItemCount(): Int {
        return NotesList.notesList.size
    }

    override fun onBindViewHolder(p0: NoteViewHolder, p1: Int) {

        // format note timestamp
        val timestampFormat = SimpleDateFormat("dd/MM/yy HH:mm")

        // display note properties
        p0.noteTitleTextView?.text = NotesList.notesList[p1].noteTitle
        p0.noteTimestampTextView?.text = timestampFormat.format(NotesList.notesList[p1].noteTimestamp).toString()

        // TODO onclick listener to edit clicked note
    }

}
