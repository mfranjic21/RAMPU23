package com.example.iznajmljivanjevozila

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.iznajmljivanjevozila.data.carsList
import com.example.iznajmljivanjevozila.fragments.Menu

class MainActivity : AppCompatActivity() {
    private lateinit var carList : RecyclerView
    private var currentFilter: String = "Marka"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(carsList.size == 0) {
            fillCarView()
        }

        if(!SessionManager.isLoggedIn()){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val profil = findViewById<ImageButton>(R.id.profil_pocetna)

        profil.setOnClickListener {
            val intent = Intent (this, Menu::class.java)
            startActivity(intent)
        }

        carList = findViewById(R.id.carReservation)
        carList.layoutManager = LinearLayoutManager(this)
        carList.adapter = CarListAdapter(carsList)

        fillFilterOptions()
        setSearchViewListener()
    }

    private fun fillCarView() {
        carsList.add(Cars("Ford", "Focus", "2017", 4.5, "PŽ-1125-GS", "149,550 km", "Dizel, Manualni mjenjač, Broj sjedala: 5", true, "", R.drawable.ford_focus))
        carsList.add(Cars("Škoda", "Octavia", "2022", 4.9, "OS-5694-DK", "4,000 km", "Dizel, Automatski mjenjač, Broj sjedala:5", true, "", R.drawable.skoda_octavia))
        carsList.add(Cars("Audi", "A4", "2009", 3.9, "PŽ-452-FG", "140,382 km", "Benzin, Manualni mjenjač, Broj sjedala: 5", true, "", R.drawable.audi_a4))
        carsList.add(Cars("Volkswagen", "Tiguan", "2019", 4.2, "VŽ-2152-KT", "67,415 km", "Dizel, Automatski mjenjač, Broj sjedala:5", true, "", R.drawable.vw_tiguan))
        carsList.add(Cars("Mercedes-Benz", "C-class", "2018", 4.3, "VŽ-2152-KT", "90,419 km", "Dizel, Automatski mjenjač, Broj sjedala:4", true, "", R.drawable.mercedesbenz_cclass))
        carsList.add(Cars("Volvo", "V40", "2016", 3.7, "SB-125-BN", "110,792 km", "Dizel, Manualni mjenjač, Broj sjedala:5", true, "", R.drawable.volvo_v40))
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
                    carsList.clear()
                    fillCarView()
                    carList.adapter?.notifyDataSetChanged()
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
        carList.adapter?.notifyDataSetChanged()
    }

}