package com.talithariddiford.mindtoolsapp.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ActivityEntity::class],
    version = 1, // Set this to 1 for your first version; increment if you change schema
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
//    ]
)
@TypeConverters(ActivityTypeConverters::class)
abstract class ActivityDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}


