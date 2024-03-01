package com.example.betus.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.betus.DataClass.Users
import com.example.betus.R
import com.example.betus.databinding.ActivitySigninBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException

class Signin : AppCompatActivity() {

//    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private lateinit var storageRef: StorageReference
    private val mFireStore = FirebaseFirestore.getInstance()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))   // Type google id here
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
        storageRef = FirebaseStorage.getInstance().reference

        binding.btnGoogle.setOnClickListener {
            showProgressBar()
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }

        progressBar = findViewById(R.id.progressBar)

        binding.select.setOnClickListener {
            select()
        }

        binding.upload.setOnClickListener {
            if (binding.name.text.toString().isEmpty() || binding.clgName.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Upload Image after filling all other entries",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                showProgressBar()
                Toast.makeText(
                    this,
                    "please Wait : While your account is being created",
                    Toast.LENGTH_SHORT
                ).show()
                upload()
            }
        }
    }

    private fun registerUser(activity: Signin, userInfo: Users) {
        mFireStore.collection("Users")
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                hideProgressBar()
                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))

            }
            .addOnFailureListener { e ->
                hideProgressBar()
                Log.e("registerUser", "Error registering user", e)
                Toast.makeText(activity, "Error registering user: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun upload() {
        if (filePath != null) {
            val ref = storageRef.child("images/${getCurrentUserId()}")

            ref.putFile(filePath!!)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                    Toast.makeText(this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show()

                    ref.downloadUrl.addOnCompleteListener { uriTask ->
                        if (uriTask.isSuccessful) {
                            val downloadUrl = uriTask.result
                            val clg_name = binding.name.text.toString()
                            val name = binding.clgName.text.toString()
                            val points = "500"
                            val imageUrl = downloadUrl.toString()
                            val userId = getCurrentUserId()

                            val user = Users(name, imageUrl, clg_name, points, userId)
                            registerUser(this, user)

                            if (clg_name.isBlank() && name.isBlank()) {
                                Toast.makeText(this, "Fields Can't be blank", Toast.LENGTH_SHORT)
                                    .show()
                                return@addOnCompleteListener
                            }
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Image Upload Failed: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                Glide.with(this).load(filePath).into(binding.img)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                hideProgressBar()
                                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            } else {
                                hideProgressBar()
                                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }

    private fun select() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid ?: ""
    }

    private fun showProgressBar() {
        progressBar.visibility = android.view.View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = android.view.View.GONE
    }
}
