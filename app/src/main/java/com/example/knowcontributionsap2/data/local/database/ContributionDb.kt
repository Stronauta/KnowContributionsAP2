package com.example.knowcontributionsap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.knowcontributionsap2.data.local.entities.ContributionsEntity
import com.example.knowcontributionsap2.data.local.dao.ContributionDao

@Database(
    entities = [
        ContributionsEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class ContributionDb : RoomDatabase() {
    abstract fun contributionDao(): ContributionDao
}