package com.example.happyplaces.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class HappyPlacesModel (
    val id :Int,
    val title :String?,
    val image:String?,
    val description:String?,
    val date :String?,
    val location :String?,
    val latitude : Double,
    val longitude :Double

):Serializable