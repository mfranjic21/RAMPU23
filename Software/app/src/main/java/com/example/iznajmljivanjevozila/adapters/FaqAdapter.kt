package com.example.iznajmljivanjevozila.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.helpers.FaqViewHolder
import com.example.iznajmljivanjevozila.data.Questions
import com.example.iznajmljivanjevozila.R

class FaqAdapter(private val faqList: List<Questions>) : RecyclerView.Adapter<FaqViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.faq_card_view, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val faq = faqList[position]
        holder.naslovPitanja.text = faq.question
        holder.odgovorPitanja.text = faq.answer
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}
