package com.example.betus.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betus.Adapter.LeaderBoardAdapter
import com.example.betus.Adapter.StatusAdapter
import com.example.betus.DataClass.Users
import com.example.betus.R
import com.example.betus.databinding.FragmentLeaderBoardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User

class LeaderBoard : Fragment() {

    private lateinit var binding: FragmentLeaderBoardBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var statusAdapter: StatusAdapter
    private lateinit var leaderBoardAdapter: LeaderBoardAdapter
    private lateinit var UserClass: MutableList<Users>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLeaderBoardBinding.inflate(layoutInflater,container,false)

        auth = FirebaseAuth.getInstance()

        UserClass = mutableListOf()

        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.statusRV.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL ,false)



        leaderBoardAdapter = LeaderBoardAdapter(requireContext(), UserClass)
        statusAdapter = StatusAdapter(requireContext(), UserClass)

        binding.rv.adapter = leaderBoardAdapter
        binding.statusRV.adapter = statusAdapter

        fetchfromfirestore()


        return (binding.root)
    }

    private fun fetchfromfirestore() {

        FirebaseFirestore.getInstance().collection("Users").orderBy("points", Query.Direction.DESCENDING).get().addOnSuccessListener { documents->
            for(document in  documents){

                val name = document.getString("name")?:""
                val points = document.getString("points")?:""
                val id = document.getString("id")?:""
                val image = document.getString("image")?:""
                val colName = document.getString("colName")?:""

                Toast.makeText(requireContext(), "hi $name", Toast.LENGTH_SHORT).show()

                UserClass.add(Users(name , image, colName ,points , id))
            }

          leaderBoardAdapter.notifyDataSetChanged()
          statusAdapter.notifyDataSetChanged()

        }.addOnFailureListener{

            Toast.makeText(requireContext(), "Unable to Fetch data : Error 404", Toast.LENGTH_SHORT).show()
        }
    }
}