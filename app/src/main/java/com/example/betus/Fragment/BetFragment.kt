package com.example.betus.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betus.Adapter.SportAdapter
import com.example.betus.DataClass.Users
import com.example.betus.databinding.FragmentBetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class betFragment : Fragment() {

    private lateinit var binding: FragmentBetBinding
    private lateinit var sport_adapter: SportAdapter
    private lateinit var usrs: MutableList<Users>
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

        binding.sportRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,true)
        binding.sportRv.adapter = sport_adapter

        fetchFromFirestore()

        return binding.root
    }

    private fun fetchFromFirestore() {

        usrs.clear()
        FirebaseFirestore.getInstance().collection("sports").get().addOnSuccessListener {documents->

            for(document in documents){
                val docId = document.id
                val name  = document.getString("name")?:""
                val image  = document.getString("image")?:""

                usrs.add(Users(name,image))
            }
            sport_adapter.notifyDataSetChanged()
        }.addOnFailureListener{
            Toast.makeText(requireContext(), "unable TO fetch from sportadapter", Toast.LENGTH_SHORT).show()
        }
    }


}

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