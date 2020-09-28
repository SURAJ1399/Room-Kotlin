package net.piedevelopers.roomkotlin.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


//entity are table in room arch and attributes are column names

@Entity
data  class Note (

   // @ColumnInfo(name = "column_name of your choice") if you want other name than variable name
    val title:String,
    val note:String





):Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}