package net.piedevelopers.roomkotlin.db

import androidx.room.*


//dao is always a interface clss all db operation done here(data acess object)

@Dao
interface NoteDao {
    @Insert
    fun addNote(note:Note)

    @Query("Select *From note Order by id desc" )
    fun getAllNotes():List<Note>

    @Insert
    fun addMultiplesNotes(vararg note:Note)

    @Update
    fun UpdateNote(note:Note)

    @Delete
    fun DeleteNote(note :Note)


}