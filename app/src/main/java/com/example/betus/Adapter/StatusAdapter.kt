package com.example.betus.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betus.R
import com.example.betus.DataClass.Users

//this adapter is for status_rv

class StatusAdapter(
    val context:Context,
    val list: MutableList<Users>
):RecyclerView.Adapter<StatusAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.status_item_view, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = list[position]

        Glide
            .with(context)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.image)

        holder.name.text = user.name
    }


    inner class ViewHolder(item : View):RecyclerView.ViewHolder(item){

        val name:TextView = item.findViewById(R.id.statusName)
        val image:ImageView = item.findViewById(R.id.statusProfile)
    }
}