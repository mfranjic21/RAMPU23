package com.example.iznajmljivanjevozila

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.iznajmljivanjevozila.fragments.Login
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.adapters.CarListAdapter
import com.example.iznajmljivanjevozila.data.Cars
import com.example.iznajmljivanjevozila.data.Reviews
import com.example.iznajmljivanjevozila.data.User
import com.example.iznajmljivanjevozila.data.carsList
import com.example.iznajmljivanjevozila.data.reviewsList
import com.example.iznajmljivanjevozila.data.userList
import com.example.iznajmljivanjevozila.fragments.Menu
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

class MainActivity : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var carList : RecyclerView
    private var currentFilter: String = "Marka"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        val profil = findViewById<ImageButton>(R.id.profil_pocetna)

        profil.setOnClickListener {
            val intent = Intent (this, Menu::class.java)
            startActivity(intent)
        }

        if(carsList.isEmpty()){
            fillCars()
        }

        fillReviewsList()
        fillUserList()

        fillFilterOptions()
        setSearchViewListener()
    }

    private fun fillCarView() {

        carList = findViewById(R.id.carReservation)
        carList.layoutManager = LinearLayoutManager(this)
        carList.adapter = CarListAdapter(carsList, false, this)
    }

    private fun fillCars() {
        carsList.clear()
        val carsRef = database.getReference("vehicles")

        carsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (questionSnapshot in dataSnapshot.children) {
                        val key = questionSnapshot.key.toString()
                        val mark = questionSnapshot.child("mark").value.toString()
                        val model = questionSnapshot.child("model").value.toString()
                        val year = questionSnapshot.child("year").value.toString()
                        val registrationPlate = questionSnapshot.child("registrationPlate").value.toString()
                        val currentMileage = questionSnapshot.child("currentMileage").value.toString()
                        val details = questionSnapshot.child("details").value.toString()
                        val availability = questionSnapshot.child("availability").value as Boolean
                        val reservationUser = questionSnapshot.child("reservationUser").value.toString()
                        val photo = questionSnapshot.child("photo").value.toString()

                        carsList.add(Cars(key, mark, model, year, registrationPlate, currentMileage, details, availability, reservationUser, photo))
                    }

                    fillCarView()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun fillReviewsList() {
        reviewsList.clear()
        val reviewsRef = database.getReference("reviews")

        reviewsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("TEST", "Getting all cars")
                if (dataSnapshot.exists()) {
                    for (questionSnapshot in dataSnapshot.children) {
                        val car = questionSnapshot.child("vehicle").value.toString()
                        val user = questionSnapshot.child("user").value.toString()
                        val grade = questionSnapshot.child("grade").value.toString()
                        val comment = questionSnapshot.child("comment").value.toString()

                        reviewsList.add(Reviews(car, user, grade.toFloat(), comment))
                    }
                }
                fillCarView()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun fillUserList() {
        userList.clear()
        val usersRef = database.getReference("users")

        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (questionSnapshot in dataSnapshot.children) {
                        val firstname = questionSnapshot.child("firstname").value.toString()
                        val lastname = questionSnapshot.child("lastname").value.toString()
                        val email = questionSnapshot.child("email").value.toString()
                        val username = questionSnapshot.child("username").value.toString()
                        val password = questionSnapshot.child("password").value.toString()

                        userList.add(User(firstname, lastname, email, questionSnapshot.key.toString(), password))
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }


    private fun fillFilterOptions(){
        val filterSpinner = findViewById<Spinner>(R.id.main_filter)
        val filterOptions = arrayOf("Marka", "Model", "Godina", "Kilometraža do")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, filterOptions)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = adapter

        filterSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                currentFilter = filterOptions[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        })
    }

    private fun setSearchViewListener(){
        val searchView = findViewById<SearchView>(R.id.main_search_bar)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    filterCarView(query, currentFilter)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText == ""){
                    fillCars()
                }
                return true
            }
        })
    }

    private fun filterCarView(filterValueArg: String, filterType: String){
        val filterValue = filterValueArg.lowercase()
        val filter: Array<Pair<String, String>> = arrayOf(
            Pair("Marka", "mark"),
            Pair("Model", "model"),
            Pair("Godina", "year"),
            Pair("Kilometraža do", "currentMileage")
        )

        val actualFilter = filter.firstOrNull { it.first == filterType }?.second

        if(actualFilter != null){
            val vehicleRef = database.getReference("vehicles")
            val query = vehicleRef.orderByChild(actualFilter)

            query.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("TEST", "Getting filtered cars")
                    Log.d("TEST", actualFilter)
                    val filteredCarsList = mutableListOf<Cars>()
                    for (vehicleSnapshot in snapshot.children){
                        val key = vehicleSnapshot.key.toString()
                        val mark = vehicleSnapshot.child("mark").value.toString()
                        val model = vehicleSnapshot.child("model").value.toString()
                        val year = vehicleSnapshot.child("year").value.toString()
                        val registrationPlate = vehicleSnapshot.child("registrationPlate").value.toString()
                        val currentMileage = vehicleSnapshot.child("currentMileage").value.toString()
                        val details = vehicleSnapshot.child("details").value.toString()
                        val availability = vehicleSnapshot.child("availability").value as Boolean
                        val reservationUser = vehicleSnapshot.child("reservationUser").value.toString()
                        val photo = vehicleSnapshot.child("photo").value.toString()

                        val vehicle = Cars(key, mark, model, year, registrationPlate, currentMileage, details, availability, reservationUser, photo)

                        when (actualFilter) {
                            "mark" -> {
                                if(vehicle.mark.contains(filterValue, ignoreCase = true)){
                                    filteredCarsList.add(vehicle)
                                }
                            }
                            "model" -> {
                                if(vehicle.model.contains(filterValue, ignoreCase = true)){
                                    filteredCarsList.add(vehicle)
                                }
                            }
                            "year" -> {
                                if(vehicle.year.contains(filterValue, ignoreCase = true)){
                                    filteredCarsList.add(vehicle)
                                }
                            }
                            "currentMileage" -> {
                                val numericValue =
                                    vehicle.currentMileage.replace("[^\\d]".toRegex(), "")
                                        .toIntOrNull()
                                if (numericValue != null && numericValue < filterValue.toInt()) {
                                    filteredCarsList.add(vehicle)
                                }
                            }
                        }
                    }
                    for(car in filteredCarsList){
                        Log.d("TEST", car.toString())
                    }
                    carsList = filteredCarsList
                    fillCarView()
                    Log.d("TEST", carsList.toString())
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }


        /*
        carsList.retainAll { car ->
            when (actualFilter) {
                "mark" -> car.mark.contains(filterValue, ignoreCase = true)
                "model" -> car.model.contains(filterValue, ignoreCase = true)
                "year" -> car.year.contains(filterValue, ignoreCase = true)
                "currentMileage" -> {
                    val numericValue = car.currentMileage.replace("[^\\d]".toRegex(), "").toIntOrNull()
                    numericValue != null && numericValue < filterValue.toInt()
                }
                else -> false
            }
        }
         */

    }

}
