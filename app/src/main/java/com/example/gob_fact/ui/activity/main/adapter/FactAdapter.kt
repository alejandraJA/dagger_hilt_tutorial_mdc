package com.example.gob_fact.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.FactEntity

class FactAdapter(
    private val facts: List<FactEntity>,
    private val factInterface: (Int) -> Unit
) : RecyclerView.Adapter<FactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FactViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_fact, parent, false),
        factInterface
    )

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(facts[position])
    }

    override fun getItemCount(): Int {
        return facts.size
    }

}