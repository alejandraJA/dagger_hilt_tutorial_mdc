package com.example.gob_fact.ui.fragment.main.adapter

import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.databinding.ItemFactBinding
import com.example.gob_fact.sys.util.Constants
import com.squareup.picasso.Picasso

class FactViewHolder(
    itemView: View,
    private val movieInterface: (Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemFactBinding.bind(itemView)

    fun bind(movieEntity: FactEntity) {
        binding.fact = movieEntity
        binding.cardFact.setOnClickListener {
            movieInterface(absoluteAdapterPosition)
        }
    }

}