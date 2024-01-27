package com.example.iznajmljivanjevozila.helpers

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.data.Notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Notifications : Service(){
    val userNotifications = mutableListOf<Notification>()
    private var id = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val database = Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")
        val vehicleRef = database.getReference("vehicles")
        val notificationRef = database.getReference("notifications")

        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        if(uid != null){
            vehicleRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(vehicleSnapshot in dataSnapshot.children){
                        val vehicleKey = vehicleSnapshot.key.toString()
                        val matchingNotification = userNotifications.find { notification ->
                            notification.vehicle == vehicleKey
                        }

                        if (matchingNotification != null){
                            if(vehicleSnapshot.child("availability").value == true){
                                sendLocalNotification("Vozilo " + vehicleSnapshot.child("mark").value + " je ponovno dostpuno")
                            }
                        }
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        }


        notificationRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userNotifications.clear()
                for (notificationSnapshot in snapshot.children) {

                    val user = notificationSnapshot.child("user").value.toString()
                    val vehicle = notificationSnapshot.child("vehicle").value.toString()


                    if(user == uid){
                        userNotifications.add(Notification(user,vehicle))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onDestroy() {
        //databaseReference.removeEventListener(valueEventListener)
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun sendLocalNotification(message: String) {
        val notification =
            NotificationCompat.Builder(applicationContext, "availability")
                .setContentTitle("Vozilo je dostupno")
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setSmallIcon(R.drawable.notification_bell)
                .build()

        with(NotificationManagerCompat.from(this)) {
            notify(++id, notification)
            //removeNotification()
        }

    }
}
