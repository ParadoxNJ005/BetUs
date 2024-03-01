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

//this adapter is for leaderboard_rv

class LeaderBoardAdapter(
    val context:Context,
    val list: MutableList<Users>
):RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.user_item_view, parent,false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val user = list[position]

        Glide
            .with(context)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.image)

        holder.name.text = user.name
        holder.rank.text = (position+1).toString()
        holder.points.text = user.points

    }


    inner class MyViewHolder(item : View):RecyclerView.ViewHolder(item){

        val name:TextView = item.findViewById(R.id.name)
        val rank:TextView = item.findViewById(R.id.rank)
        val points:TextView = item.findViewById(R.id.points)
        val image:ImageView = item.findViewById(R.id.image)

    }
}