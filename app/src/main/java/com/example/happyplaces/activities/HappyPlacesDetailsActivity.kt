package com.example.happyplaces.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happyplaces.R
import com.example.happyplaces.model.HappyPlacesModel
import kotlinx.android.synthetic.main.activity_happy_places_details.*

class HappyPlacesDetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_happy_places_details)

        setSupportActionBar(toolbar_happy_place_detail) // Use the toolbar to set the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // This is to use the home back button.
        // Setting the click event to the back button
        toolbar_happy_place_detail.setNavigationOnClickListener {
            onBackPressed()
        }
        var happyPlacesDetailModel:HappyPlacesModel?=null
        if(intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)){
            happyPlacesDetailModel=intent.getSerializableExtra(MainActivity.EXTRA_PLACE_DETAILS)as HappyPlacesModel
        }

        if (happyPlacesDetailModel != null) {

            setSupportActionBar(toolbar_happy_place_detail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = happyPlacesDetailModel.title

            toolbar_happy_place_detail.setNavigationOnClickListener {
                onBackPressed()
            }

            iv_place_image.setImageURI(Uri.parse(happyPlacesDetailModel.image))
            tv_description.text = happyPlacesDetailModel.description
            tv_location.text = happyPlacesDetailModel.location
        }

    }
}