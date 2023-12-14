package com.example.dagger_hilt.ui.activity.main.adapter

import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity
import com.example.dagger_hilt.databinding.ItemMovieBinding
import com.example.dagger_hilt.sys.util.Constants
import com.squareup.picasso.Picasso

class MovieViewHolder(itemView: View, private val movieInterface: (Int, Boolean) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val binding = ItemMovieBinding.bind(itemView)
    fun bind(movieEntity: MovieEntity) {
        binding.movie = movieEntity
        binding.checkboxLike.setOnCheckedChangeListener { _: CompoundButton?, b: Boolean ->
            if (movieEntity.like != b) movieInterface.invoke(
                movieEntity.id,
                b
            )
        }
        binding.checkboxLike.isChecked = movieEntity.like
        Picasso.get()
            .load(Constants.BASE_URL_IMAGES + movieEntity.posterPath)
//          .placeholder(R.drawable.ic_round_person_24)
//          .error(R.drawable.ic_round_error_24)
            .noFade()
            .into(binding.imagePoster)
    }
}