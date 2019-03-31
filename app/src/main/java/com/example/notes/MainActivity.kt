package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.notes.adapters.NotesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
            startActivity(startNoteDetailsActivityIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.notifyDataSetChanged()
    }

}
