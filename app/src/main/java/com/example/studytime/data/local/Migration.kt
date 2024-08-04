package com.example.studytime.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // For example, if you added a new column to an existing table
        database.execSQL("ALTER TABLE Subject ADD COLUMN imagePath TEXT")
    }
}
