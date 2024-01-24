package com.example.iznajmljivanjevozila.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.iznajmljivanjevozila.R

class AppConfiguration : AppCompatActivity() {

    private lateinit var switchMode: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.postavke_aplikacije)

        val natragButton = findViewById<ImageButton>(R.id.natrag)

        natragButton.setOnClickListener {
            val intent = Intent (this, Menu::class.java)
            startActivity(intent)
        }

        switchMode = findViewById(R.id.switchMode)

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)

        val nightMode = resources.configuration.uiMode and UI_MODE_NIGHT_MASK
        val isSystemDarkMode = nightMode == UI_MODE_NIGHT_YES


        switchMode.isChecked = isSystemDarkMode
        AppCompatDelegate.setDefaultNightMode(if (isSystemDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)


        switchMode.setOnClickListener(View.OnClickListener {
            if (switchMode.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences.edit()
                editor.putBoolean("nightMode", true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences.edit()
                editor.putBoolean("nightMode", false)
            }
            editor.apply()
        })
    }
}

