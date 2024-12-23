package com.example.gob_fact.ui.main.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.databinding.ItemFactBinding

class FactViewHolder(
    itemView: View,
    private val movieInterface: (FactEntity) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemFactBinding.bind(itemView)

    fun bind(factEntity: FactEntity) {
        binding.fact = factEntity
        binding.cardFact.setOnClickListener {
            movieInterface(factEntity)
        }
    }

}