@file:Suppress("DEPRECATION")

package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),
    NoteClickDeleteInterface, NoteClickInterface {

   private lateinit var binding: ActivityMainBinding
   private lateinit var database: NoteDatabase
   lateinit var viewModal: NoteViewModel
   lateinit var adapter: NoteRVAdapter
   lateinit var selectedNote: Note
   lateinit var notesRV:RecyclerView
   lateinit var addFAB:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notesRV = findViewById(R.id.recycler_view)
        addFAB = findViewById(R.id.fb_add_note)
        notesRV.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteRVAdapter(this, this,this)
        notesRV.adapter = noteRVAdapter
        viewModal = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModal.allNotes.observe(this, Observer { list->
            list?.let {
               noteRVAdapter.updateList(it)
            }
        })

        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity,AddNote::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onDeleteIconClick(note: Note) {
        viewModal.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity,AddNote::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteID",note.id)
        startActivity(intent)
        this.finish()
    }


}