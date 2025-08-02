package com.talithariddiford.mindtoolsapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityEntity)

    @Query("SELECT * FROM activity")
    fun getActivities(): Flow<List<ActivityEntity>>

    @Update
    suspend fun update(activity: ActivityEntity)

    @Delete
    suspend fun delete(activity: ActivityEntity)

    @Query("SELECT * FROM activity WHERE id = :id LIMIT 1")
    suspend fun getActivityById(id: String): ActivityEntity?


    // Example for filtering by mood (assuming you add a field)
    // @Query("SELECT * FROM activity WHERE :mood IN (helpfulnessByMood)")
    // fun getActivitiesByMood(mood: String): Flow<List<ActivityEntity>>
}
