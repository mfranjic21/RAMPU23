package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.FaqAdapter
import com.example.iznajmljivanjevozila.data.Questions
import com.example.iznajmljivanjevozila.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database


class CustomerService : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    var faqList = mutableListOf<Questions>()
    var yourQuestions = mutableListOf<Questions>()
    lateinit var faqPitanja: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_service)

        faqPitanja = findViewById(R.id.faqQuestions)
        database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")
        var uid = Firebase.auth.currentUser!!.uid

        loadQuestions(uid)

        val natragButton = findViewById<ImageButton>(R.id.btnBack)
        val faqButton = findViewById<Button>(R.id.faq)
        val vasaPitanjaButton = findViewById<Button>(R.id.vasaPitanja)
        val posaljiNovoPitanjeButton = findViewById<ImageButton>(R.id.addRecension)

        natragButton.setOnClickListener {
            val intent = Intent (this, Menu::class.java)
            startActivity(intent)
        }

        faqButton.setOnClickListener {
            faqButton.setBackgroundResource(R.drawable.arrow_border)
            vasaPitanjaButton.setBackgroundResource(android.R.color.transparent)

            RefreshList(faqList)
        }

        vasaPitanjaButton.setOnClickListener {
            vasaPitanjaButton.setBackgroundResource(R.drawable.arrow_border)
            faqButton.setBackgroundResource(android.R.color.transparent)

            RefreshList(yourQuestions)
        }

        posaljiNovoPitanjeButton.setOnClickListener {
            val inputPitanjaText = findViewById<EditText>(R.id.inputPitanja)

            vasaPitanjaButton.setBackgroundResource(R.drawable.arrow_border)
            faqButton.setBackgroundResource(android.R.color.transparent)

            if (inputPitanjaText.text.toString() != "") {
                addQuestion(inputPitanjaText.text.toString(), uid)
                RefreshList(yourQuestions)
            }

            inputPitanjaText.setText("")
        }
    }

    private fun loadQuestions(uid: String){
        faqList.clear()
        yourQuestions.clear()
        loadQuestionsSeparate("questions", uid)
        loadQuestionsSeparate("personalquestions", uid)
    }

    private fun loadQuestionsSeparate(typeOfQuestions: String, uid: String) {
        val questionsRef = database.getReference(typeOfQuestions)

        questionsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (questionSnapshot in dataSnapshot.children) {
                        val question = questionSnapshot.child("question").value.toString()
                        val answer = questionSnapshot.child("answer").value.toString()

                        if (typeOfQuestions == "personalquestions"){
                            if (uid == questionSnapshot.child("user").value.toString()){
                                yourQuestions.add(Questions(question, answer))
                            }
                        } else{
                            faqList.add(Questions(question, answer))
                        }
                    }

                    fillView()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun fillView() {
        val faqQuestions = findViewById<RecyclerView>(R.id.faqQuestions)
        faqQuestions.layoutManager = LinearLayoutManager(this)
        faqQuestions.adapter = FaqAdapter(faqList)
    }

    private fun RefreshList(list: MutableList<Questions>) {
        faqPitanja.layoutManager = LinearLayoutManager(this)
        faqPitanja.adapter = FaqAdapter(list)
    }

    private fun addQuestion(question: String, uid: String) {
        val questionsRef = database.getReference("personalquestions")

        val newQuestionKey = questionsRef.push().key

        if (newQuestionKey != null) {
            val questionData = HashMap<String, Any>()
            questionData["user"] = uid
            questionData["question"] = question
            questionData["answer"] = "Čeka se odgovor administratora"

            questionsRef.child(newQuestionKey).setValue(questionData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        yourQuestions.add(Questions(question, "Čeka se odgovor administratora"))
                        RefreshList(yourQuestions)
                    } else {
                    }
                }
        }
    }


}




