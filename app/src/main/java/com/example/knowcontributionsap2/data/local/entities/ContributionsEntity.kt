package com.example.knowcontributionsap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Contributions")
data class ContributionsEntity(
    @PrimaryKey
    val contributionId: Int? = null,
    var nombre : String = "",
    var monto : Double = 0.0,
    var descripcion : String = "",
    var fecha : Date? = null
)
