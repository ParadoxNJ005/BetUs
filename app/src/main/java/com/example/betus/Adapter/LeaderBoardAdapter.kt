package com.example.betus.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    }


    inner class MyViewHolder(item : View):RecyclerView.ViewHolder(item)
}