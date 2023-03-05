package com.example.weatherreportcompose.Model.DB

class LocationsRepository(private val locationsDao: LocationDao) {

    val dao = locationsDao
    fun addLocation(location: Location){
        locationsDao.insert(location)
    }
    fun getLocations(): List<Location>{
        return locationsDao.getAll()
    }
    fun removeLocation(location: Location){
        locationsDao.delete(location)
    }

}