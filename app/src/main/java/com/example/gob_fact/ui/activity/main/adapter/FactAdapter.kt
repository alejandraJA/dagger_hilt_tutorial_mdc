package com.example.gob_fact.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.MovieEntity

class FactAdapter(
    private val quoteList: List<MovieEntity>,
    private val movieInterface: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<FactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FactViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false),
        movieInterface
    )

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(quoteList[position])
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

}