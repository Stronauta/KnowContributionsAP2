package com.example.knowcontributionsap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.knowcontributionsap2.data.local.entities.ContributionsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContributionDao {
    @Upsert()
    suspend fun save(contribution: ContributionsEntity)

    @Query(
        """
        SELECT * 
        FROM Contributions 
        WHERE contributionId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): ContributionsEntity?

    @Delete
    suspend fun delete(contribution: ContributionsEntity)

    @Query("SELECT * FROM Contributions")
    fun getAll(): Flow<List<ContributionsEntity>>
}