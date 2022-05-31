package com.example.placar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<ItemViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]


        // sets the text to the textview from our itemHolder class
        holder.pointHome.text = ItemsViewModel.pointHome.toString();
        holder.pointAway.text = ItemsViewModel.pointAway.toString();
        holder.playerHome.text = ItemsViewModel.textHome
        holder.playerAway.text = ItemsViewModel.textAway

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val pointHome: TextView = itemView.findViewById(R.id.pointHome)
        val pointAway: TextView = itemView.findViewById(R.id.pointAway)
        val playerHome: TextView = itemView.findViewById(R.id.homeTV)
        val playerAway: TextView = itemView.findViewById(R.id.awayTV)
    }
}
