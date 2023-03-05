package com.example.weatherreportcompose.Model.DB

import android.content.Context
import androidx.room.*

class LocationsDB {
}


@Entity
data class Location(
    @PrimaryKey(autoGenerate = true) val locationId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
)


@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun getAll(): List<Location>

    @Insert
    fun instertAll(vararg location: Location)

    @Delete
    fun delete(location: Location)

    @Insert
    fun insert(location: Location)

}


@Database(entities = [Location::class], version = 2)
abstract class LocationsDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        private var INSTANCE: LocationsDatabase? = null
        fun getInstance(context: Context):
                LocationsDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        LocationsDatabase::class.java,
                    "locations_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}