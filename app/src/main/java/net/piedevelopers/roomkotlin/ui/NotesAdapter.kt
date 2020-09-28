package net.piedevelopers.roomkotlin.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_layout.view.*
import net.piedevelopers.roomkotlin.R
import net.piedevelopers.roomkotlin.db.Note

class NotesAdapter (val notes:List<Note>) :RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {



        return  NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false))
    }

    override fun getItemCount(): Int {
   return    notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    holder.view.text_view_title.text=notes[position].title
        holder.view.text_view_note.text=notes[position].note

        holder.view.setOnClickListener {

val action=HomeFragmentDirections.actionAddNote()
            action.noteArg=notes[position]
            Navigation.findNavController(it).navigate(action)
        }
    }


    class NoteViewHolder(val view: View):RecyclerView.ViewHolder(view)

}