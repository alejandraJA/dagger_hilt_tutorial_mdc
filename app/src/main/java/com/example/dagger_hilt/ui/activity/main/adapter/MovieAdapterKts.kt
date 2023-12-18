package com.example.dagger_hilt.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger_hilt.R
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts

class MovieAdapterKts(
    private val quoteList: List<MovieEntityKts>,
    private val movieInterface: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<MovieViewHolderKts>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolderKts(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false),
        movieInterface
    )

    override fun onBindViewHolder(holder: MovieViewHolderKts, position: Int) {
        holder.bind(quoteList[position])
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

}