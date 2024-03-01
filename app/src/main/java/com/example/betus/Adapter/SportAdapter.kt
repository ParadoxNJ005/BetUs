package com.example.betus.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.betus.DataClass.Sports
import com.example.betus.R
import com.example.betus.DataClass.Users

class SportAdapter(
    val context: Context,
    val list: MutableList<Sports>
) : RecyclerView.Adapter<SportAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.bet_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(context)
            .load(list[position].clgImg1)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.clg1_img)

        Glide
            .with(context)
            .load(list[position].clgImg2)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.clg2_img)

        holder.clg1_name.text = list[position].clgName1
        holder.clg2_name.text = list[position].clgName2
        holder.match_name.text = list[position].match
        holder.time.text = list[position].time
        holder.date.text = list[position].date

    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val clg1_img: ImageView = item.findViewById(R.id.clgImg1)
        val clg2_img: ImageView = item.findViewById(R.id.clgImg2)
        val clg1_name: TextView = item.findViewById(R.id.clgName1)
        val clg2_name: TextView = item.findViewById(R.id.clgName2)
        val time: TextView = item.findViewById(R.id.time)
        val date: TextView = item.findViewById(R.id.date)
        val match_name: TextView = item.findViewById(R.id.matchName)
    }
}
