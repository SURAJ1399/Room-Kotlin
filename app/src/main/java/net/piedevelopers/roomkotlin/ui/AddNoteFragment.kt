package net.piedevelopers.roomkotlin.ui

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.piedevelopers.roomkotlin.R
import net.piedevelopers.roomkotlin.db.Note
import net.piedevelopers.roomkotlin.db.NoteDao
import net.piedevelopers.roomkotlin.db.NoteDatabase

class AddNoteFragment : BaseFragment() {
private var note:Note?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.let {
            note=AddNoteFragmentArgs.fromBundle(it).noteArg
            edit_text_title.setText(note?.title)
            edit_text_note.setText(note?.note)
        }

        button_save.setOnClickListener {view->
    val notetitle=edit_text_title.text.toString().trim()
    val notebody=edit_text_note.text.toString().trim()
    if (notetitle.isEmpty() )
    {
        edit_text_title.error="Title Required"
        edit_text_title.requestFocus()
        return@setOnClickListener
    }

    if (notebody.isEmpty() )
    {
        edit_text_note.error="Note Required"
        edit_text_note.requestFocus()
        return@setOnClickListener
    }



    //roomdatabase cant be acessed on main thread so here used Asyn task
   // saveNote(note)

    launch {
        context?.let {
            val mnote=Note(notetitle,notetitle)
            if(note==null)
            {
                NoteDatabase(activity!!).getNoteDao().addNote(mnote)

            }
            else
            {
                mnote.id= note!!.id
                NoteDatabase(activity!!).getNoteDao().UpdateNote(mnote)

            }

         //  it.toast("Note Saved")
            Log.i("res","Added")
            CoroutineScope(Main).launch {
                val action=AddNoteFragmentDirections.actonsavenote()
                Navigation.findNavController(view).navigate(action)
            }


        }

    }

}



    }
    fun deletenote(){
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure??")
            setMessage("You cannot undo this ")
            setPositiveButton("Yes"){_, _->
                launch {
                    note?.let { NoteDatabase(context).getNoteDao().DeleteNote(it)

                    }
                    CoroutineScope(Main).launch {
                        val action=AddNoteFragmentDirections.actonsavenote()
                        Navigation.findNavController(view!!).navigate(action)
                    }
                }

            }
            setNegativeButton("No"){_, _->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.delete->
                if(note!=null)
                {
                    deletenote()

                }
            else
                {
                    context?.toast("Cannot Delete..!!")
                }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

//    private  fun saveNote(note: Note)
//    {
//        class SaveNote: AsyncTask<Void, Void, Void>(){
//            override fun doInBackground(vararg params: Void?): Void? {
//
//                NoteDatabase(activity!!).getNoteDao().addNote(note)
//                return  null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//        Log.i("res","Added");
//            }
//        }
//        SaveNote().execute()
//    }
}