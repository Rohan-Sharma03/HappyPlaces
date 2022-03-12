package com.example.happyplaces.adapter

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.icu.text.Transliterator
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplaces.R
import com.example.happyplaces.activities.AddHappyPlaces
import com.example.happyplaces.activities.MainActivity
import com.example.happyplaces.databasesSQLite.DatabaseHandler
import com.example.happyplaces.model.HappyPlacesModel
import kotlinx.android.synthetic.main.item_happy_place.view.*

class HappyPlacesAdapter(private val context: Context,private var List :ArrayList<HappyPlacesModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_happy_place,parent,false))
    }


    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = List[position]
        if (holder is ViewHolder){
            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.image))
            holder.itemView.tvTitle.text=model.title
            holder.itemView.tvDescription.text=model.description
            
            holder.itemView.setOnClickListener{
                if(onClickListener!=null){
                    onClickListener!!.OnClick(position,model)
                }
            }
        
        }
    }

    override fun getItemCount(): Int {
        return List.size
    }


    /**
     * A function to edit the added happy place detail and pass the existing details through intent.
     */

    fun notifyEditedItem(activity:Activity,position: Int,requestCode:Int){
        val intent = Intent(context, AddHappyPlaces::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, List[position])
        activity.startActivityForResult(
            intent,
            requestCode
        ) // Activity is started with requestCode

        notifyItemChanged(position)

    }
    /**
     * A function to bind the onclickListener.
     */
    fun SetOnClickListener(onClickListener: OnClickListener){
        this.onClickListener=onClickListener
    }

    fun removeAt(adapterPosition: Int) {
        val db = DatabaseHandler(context)
        val isDeleted =db.deleteHappyPlace(List[adapterPosition])
        if (isDeleted > 0) {
            List.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
        }
    }


    interface OnClickListener {
        abstract fun OnClick(position: Int, model: HappyPlacesModel)
    }
    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class ViewHolder(view: View):RecyclerView.ViewHolder(view)
}