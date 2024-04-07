

package com.example.wellspring.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wellspring.database.library.LibraryDao
import com.example.wellspring.database.library.LibraryItem
import com.example.wellspring.database.reader.ReaderDao
import com.example.wellspring.database.reader.ReaderData

@Database(
    entities = [LibraryItem::class, ReaderData::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class MyneDatabase : RoomDatabase() {

    abstract fun getLibraryDao(): LibraryDao
    abstract fun getReaderDao(): ReaderDao

    companion object {

        @Volatile
        private var INSTANCE: MyneDatabase? = null

        fun getInstance(context: Context): MyneDatabase {
            /*
            if the INSTANCE is not null, then return it,
            if it is, then create the database and save
            in instance variable then return it.
            */
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyneDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}