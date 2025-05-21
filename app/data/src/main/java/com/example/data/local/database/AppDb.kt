package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.converter.Converters
import com.example.data.local.dao.RecrodsDAO
import com.example.data.local.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDb: RoomDatabase() {
    abstract fun recordsDao(): RecrodsDAO
}