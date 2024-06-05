package com.juzabel.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Test")
data class Test(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String
)
