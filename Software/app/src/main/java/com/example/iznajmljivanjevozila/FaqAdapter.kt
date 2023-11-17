package com.example.iznajmljivanjevozila

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FaqAdapter(private val faqList: List<Pitanja>) : RecyclerView.Adapter<FaqViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.faq_card_view, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val faq = faqList[position]
        holder.brojPitanja.text = faq.brojPitanja
        holder.naslovPitanja.text = faq.pitanje
        holder.odgovorPitanja.text = faq.odgovor
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}
