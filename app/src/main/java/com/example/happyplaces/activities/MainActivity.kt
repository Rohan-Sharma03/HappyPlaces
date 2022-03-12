package com.example.happyplaces.activities

import android.app.Activity
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplaces.R
import com.example.happyplaces.adapter.HappyPlacesAdapter
import com.example.happyplaces.databasesSQLite.DatabaseHandler
import com.example.happyplaces.model.HappyPlacesModel
import com.example.happyplaces.utils.SwipeToDeleteCallback
import com.example.happyplaces.utils.SwipeToEditCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main_activity)
        val actionbar = supportActionBar
        if(actionbar!=null){
            actionbar.title="Happy Places"
        }

        fabHappyPlace.setOnClickListener {
           val intent = Intent(this,AddHappyPlaces::class.java)
            startActivityForResult(intent, ADD_PLACE_ACTIVITY_REQUEST_CODE)
        }

        getHappyPlacesListFromLocal()
    }

    private fun getHappyPlacesListFromLocal() {
        val db = DatabaseHandler(this)
        val getHappyPlacesList = db.getHappyPlacesList()

        if (getHappyPlacesList.size > 0) {
            rv_happy_places_list.visibility= View.VISIBLE
            tv_no_records_available.visibility=View.GONE
            setupHappyPlaceRecyclerView(getHappyPlacesList)

        }else{
            rv_happy_places_list.visibility= View.GONE
            tv_no_records_available.visibility=View.VISIBLE
        }
    }
    private fun setupHappyPlaceRecyclerView(happyPlacesList: ArrayList<HappyPlacesModel>){
        rv_happy_places_list.layoutManager=LinearLayoutManager(this)
        rv_happy_places_list.setHasFixedSize(true)
        val placeAdapter = HappyPlacesAdapter(this,happyPlacesList)
        rv_happy_places_list.adapter=placeAdapter

        placeAdapter.SetOnClickListener(object :HappyPlacesAdapter.OnClickListener{
            override fun OnClick(position: Int, model: HappyPlacesModel) {
                val intent=Intent(this@MainActivity,HappyPlacesDetailsActivity::class.java)
                intent.putExtra(EXTRA_PLACE_DETAILS,model)
                startActivity(intent)
            }

        })
        val editSwipeHandler = object : SwipeToEditCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_happy_places_list.adapter as HappyPlacesAdapter
                adapter.notifyEditedItem(
                    this@MainActivity,
                    viewHolder.adapterPosition,
                    ADD_PLACE_ACTIVITY_REQUEST_CODE
                )
            }
        }
        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(rv_happy_places_list)

        val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_happy_places_list.adapter as HappyPlacesAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                getHappyPlacesListFromLocal() // Gets the latest list from the local database after item being delete from it.
            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(rv_happy_places_list)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK){
            if (requestCode== ADD_PLACE_ACTIVITY_REQUEST_CODE){
                getHappyPlacesListFromLocal()
            }else{
                Log.e("Activity", "Cancelled or Back Pressed")
            }
        }
    }
    companion object{
        private const val ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
        internal const val EXTRA_PLACE_DETAILS = "extra_place_details"
    }
}