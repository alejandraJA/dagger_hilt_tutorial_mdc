package com.example.dagger_hilt.ui.activity.main.adapter

import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts
import com.example.dagger_hilt.databinding.ItemMovieBinding
import com.example.dagger_hilt.sys.util.ConstantsKts
import com.squareup.picasso.Picasso

class MovieViewHolderKts(itemView: View, private val movieInterface: (Int, Boolean) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val binding = ItemMovieBinding.bind(itemView)
    fun bind(movieEntityKts: MovieEntityKts) {
        binding.movie = movieEntityKts
        binding.checkboxLike.setOnCheckedChangeListener { _: CompoundButton?, b: Boolean ->
            if (movieEntityKts.like != b) movieInterface.invoke(
                movieEntityKts.id,
                b
            )
        }
        binding.checkboxLike.isChecked = movieEntityKts.like
        Picasso.get()
            .load(ConstantsKts.BASE_URL_IMAGES + movieEntityKts.posterPath)
//          .placeholder(R.drawable.ic_round_person_24)
//          .error(R.drawable.ic_round_error_24)
            .noFade()
            .into(binding.imagePoster)
    }
}