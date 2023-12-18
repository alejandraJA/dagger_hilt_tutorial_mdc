package com.example.dagger_hilt.ui.activity.main.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;
import com.example.dagger_hilt.databinding.ItemMovieBinding;
import com.example.dagger_hilt.sys.util.Constants;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private final ItemMovieBinding binding;
    private final MovieInterface movieInterface;

    public MovieViewHolder(@NonNull View itemView, MovieInterface movieInterface) {
        super(itemView);
        binding = ItemMovieBinding.bind(itemView);
        this.movieInterface = movieInterface;
    }

    void bind(MovieEntity movieEntity) {
        binding.setMovie(movieEntity.toMovieEntityKts(movieEntity));
        binding.checkboxLike.setOnCheckedChangeListener((c, b) -> {
            if (movieEntity.getLike() != b) movieInterface.check(movieEntity.getId(), b);
        });
        binding.checkboxLike.setChecked(movieEntity.getLike());
        Picasso.get()
                .load(Constants.BASE_URL_IMAGES + movieEntity.getPosterPath())
//          .placeholder(R.drawable.ic_round_person_24)
//          .error(R.drawable.ic_round_error_24)
                .noFade()
                .into(binding.imagePoster);
    }
}
