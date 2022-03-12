package com.example.happyplaces.databasesSQLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.graphics.scaleMatrix
import com.example.happyplaces.model.HappyPlacesModel

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="HappyPlacesDataBase"
        private const val TABLE_HAPPY_PLACE="HappyPlacesTable"

//        Columns names
        private const val KEY_ID="_id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"

    }
    override fun onCreate(db: SQLiteDatabase?) {
//        creating tables with feilds
        val CREATE_HAPPY_PLACE_TABLE = ("CREATE TABLE " + TABLE_HAPPY_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)")
        db?.execSQL(CREATE_HAPPY_PLACE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_HAPPY_PLACE")
        onCreate(db)
    }

    fun addHappyPlaces(happyPlacesModel: HappyPlacesModel) :Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, happyPlacesModel.title) // HappyPlaceModelClass TITLE
        contentValues.put(KEY_IMAGE, happyPlacesModel.image) // HappyPlaceModelClass IMAGE
        contentValues.put(KEY_DESCRIPTION,happyPlacesModel.description) // HappyPlaceModelClass DESCRIPTION
        contentValues.put(KEY_DATE, happyPlacesModel.date) // HappyPlaceModelClass DATE
        contentValues.put(KEY_LOCATION, happyPlacesModel.location) // HappyPlaceModelClass LOCATION
        contentValues.put(KEY_LATITUDE, happyPlacesModel.latitude) // HappyPlaceModelClass LATITUDE
        contentValues.put(KEY_LONGITUDE, happyPlacesModel.longitude) // HappyPlaceModelClass LONGITUDE

        // Inserting Row
        val result = db.insert(TABLE_HAPPY_PLACE, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return result
    }
    /**
     * Function to update record
     */

    fun updateHappyPlace(happyPlacesModel: HappyPlacesModel):Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, happyPlacesModel.title) // HappyPlaceModelClass TITLE
        contentValues.put(KEY_IMAGE, happyPlacesModel.image) // HappyPlaceModelClass IMAGE
        contentValues.put(KEY_DESCRIPTION,happyPlacesModel.description) // HappyPlaceModelClass DESCRIPTION
        contentValues.put(KEY_DATE, happyPlacesModel.date) // HappyPlaceModelClass DATE
        contentValues.put(KEY_LOCATION, happyPlacesModel.location) // HappyPlaceModelClass LOCATION
        contentValues.put(KEY_LATITUDE, happyPlacesModel.latitude) // HappyPlaceModelClass LATITUDE
        contentValues.put(KEY_LONGITUDE, happyPlacesModel.longitude) // HappyPlaceModelClass LONGITUDE


        // Updating Row
        val success = db.update(TABLE_HAPPY_PLACE, contentValues, KEY_ID + "=" + happyPlacesModel.id, null)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }

    @SuppressLint("Range")
    fun getHappyPlacesList():ArrayList<HappyPlacesModel>{
        val happyPlacesList : ArrayList<HappyPlacesModel> =ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_HAPPY_PLACE"
        val db = this.readableDatabase

        try {
            val cursor :Cursor = db.rawQuery(selectQuery,null)
            if(cursor.moveToFirst()){
                do {
                    val place = HappyPlacesModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE))

                        )
                    happyPlacesList.add(place)
                }while (cursor.moveToNext())

            }
        }catch (e:SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return happyPlacesList
    }

    fun deleteHappyPlace(happyPlacesModel: HappyPlacesModel): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_HAPPY_PLACE, KEY_ID+"="+happyPlacesModel.id,null)
        db.close()
        return success
    }
}