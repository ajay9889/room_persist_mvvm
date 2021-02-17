package com.anushka.roomdemo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subriber_data_table")
data class Subscriber (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var email: String
)