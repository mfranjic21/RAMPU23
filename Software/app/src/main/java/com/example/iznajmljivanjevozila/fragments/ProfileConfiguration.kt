package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.SessionManager
import com.example.iznajmljivanjevozila.helpers.MockDataLoader
import com.example.iznajmljivanjevozila.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database


class ProfileConfiguration : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var txtEmail: TextView
    lateinit var txtFirstname: TextView
    lateinit var txtLastname: TextView
    lateinit var txtUsername: TextView
    lateinit var txtPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.postavke_profila)

        database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")

        txtEmail = findViewById(R.id.promjeniEmail)
        txtFirstname = findViewById(R.id.promijeni_ime)
        txtLastname = findViewById(R.id.promijeni_prezime)
        txtUsername = findViewById(R.id.promijeni_user)
        txtPassword = findViewById(R.id.promijeni_pass)

        var uid = Firebase.auth.currentUser!!.uid
        replacePlaceHolders(uid)

        val vracaj = findViewById<ImageButton>(R.id.vrati_nazad_promjena)
        vracaj.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val spremi = findViewById<Button>(R.id.spremi)

        spremi.setOnClickListener {

            val updatedUser = User(
                txtFirstname.text.toString(),
                txtLastname.text.toString(),
                txtEmail.text.toString(),
                txtUsername.text.toString(),
                txtPassword.text.toString()
            )

            updateUserData(updatedUser, uid)
        }

    }

    private fun replacePlaceHolders(uid: String) {
        val activeUser = database.getReference("users")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val firstname = dataSnapshot.child("firstname").value.toString()
                    val lastname = dataSnapshot.child("lastname").value.toString()
                    val email = dataSnapshot.child("email").value.toString()
                    val username = dataSnapshot.child("username").value.toString()
                    val password = dataSnapshot.child("password").value.toString()

                    txtFirstname.text = firstname
                    txtLastname.text = lastname
                    txtEmail.text = email
                    txtUsername.text = username
                    txtPassword.text = password
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        activeUser.child(uid).addValueEventListener(valueEventListener)
    }

    private fun updateUserData(user: User, uid: String) {
        val usersRef: DatabaseReference = database.getReference("users").child(uid)

        val updateMap = HashMap<String, Any>()
        updateMap["firstname"] = user.firstname
        updateMap["lastname"] = user.lastname
        updateMap["email"] = user.email
        updateMap["username"] = user.username
        updateMap["email"] = user.email

        usersRef.updateChildren(updateMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,"Uspješno spremljeno",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext,"Greška u spremanju",Toast.LENGTH_SHORT).show()
                }
            }
    }


}