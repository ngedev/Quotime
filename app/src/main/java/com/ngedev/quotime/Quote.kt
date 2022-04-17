package com.ngedev.quotime

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var quotes: String,
    var author: String
)