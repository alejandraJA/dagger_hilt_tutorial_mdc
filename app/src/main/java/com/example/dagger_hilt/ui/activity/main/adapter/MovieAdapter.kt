package com.example.dagger_hilt.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger_hilt.R
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity

class MovieAdapter(
    private val quoteList: List<MovieEntity>,
    private val movieInterface: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false),
        movieInterface
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(quoteList[position])
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

}