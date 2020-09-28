package net.piedevelopers.roomkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    //define all enities here
entities = [Note::class],
    version = 1
)

abstract class NoteDatabase:RoomDatabase(){
    //create function to acess dao
    abstract  fun getNoteDao():NoteDao

    companion object{

        @Volatile //volatile means its avilable for all other thread immediately
        private  var instance:NoteDatabase?=null
        val LOCK=Any()

        operator fun invoke(context:Context)= instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also {
                instance=it
            }
        }

        private fun buildDatabase(context: Context)=Room.databaseBuilder(

            context.applicationContext,
            NoteDatabase::class.java,
            "notedatabase"
        ).build()

    }

}