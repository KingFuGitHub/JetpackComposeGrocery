package com.example.jetpackcomposegrocery.repository.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_list")
data class ItemData(
    @ColumnInfo(name = "name")
    var name: String,

    @PrimaryKey
    @ColumnInfo(name = "ID")
    val ID: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int,

    @ColumnInfo(name = "date")
    val date: Long
)

