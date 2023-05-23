package com.example.travelmemories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Place name (input)
//Place location (use Google Map with Google Places API to get readable location)
//Date of travel (date picker)
//Type of travel (dropdown picker)
//Mood (slider)
//Notes (input)


@Entity(tableName = "memory")
data class MemoryEntity(
    var name: String,

    var location: String,

    var date: String,

    var type: String,

    var mood: String,

    var notes: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Int = 0
}