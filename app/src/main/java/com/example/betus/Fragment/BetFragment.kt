package com.example.betus.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betus.Adapter.SportAdapter
import com.example.betus.DataClass.Sports
import com.example.betus.databinding.FragmentBetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BetFragment : Fragment() {

    private lateinit var binding: FragmentBetBinding
    private lateinit var sport_adapter: SportAdapter
    private lateinit var usrs: MutableList<Sports>
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using data binding
        binding = FragmentBetBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        usrs = mutableListOf()
        sport_adapter = SportAdapter(requireContext(),usrs)

        binding.matchRv.layoutManager = LinearLayoutManager(requireContext())
        binding.matchRv.adapter = sport_adapter



        fetchFromFirestore()

        return binding.root
    }

    private fun fetchFromFirestore() {

        usrs.clear()
        FirebaseFirestore.getInstance().collection("Sports").get().addOnSuccessListener {documents->

            for(document in documents){

                val docId = document.id
                val clgName1  = document.getString("clgName1")?:""
                val clgName2  = document.getString("clgName2")?:""
                val clgImg1  = document.getString("clgImg1")?:""
                val clgImg2  = document.getString("clgImg2")?:""
                val date = document.getString("date")?:""
                val match = document.getString("match")?:""
                val time = document.getString("time")?:""


                usrs.add(Sports(docId,clgImg1,clgImg2,clgName1,clgName2,date,match,time))
            }
            sport_adapter.notifyDataSetChanged()
        }.addOnFailureListener{
            Toast.makeText(requireContext(), "unable TO fetch from sportadapter", Toast.LENGTH_SHORT).show()
        }
    }


}















//val id:String,
//val clgImg1: String,
//val clgImg2: String,
//val clgName1: String,
//val clgName2: String,
//val date: String,
//val match:String,
//val time:String
//for(document in documents){
//    val docId = document.id
//    val name1  = document.getString("name1")?:""
//    val image1  = document.getString("image1")?:""
//    val data  = document.getString("date")?:""
//    val time  = document.getString("time")?:""
//    val name2 = document.getString("name2")?:""
//    val image2  = document.getString("image2")?:""
//    val match_type  = document.getString("image2")?:""
//
//}