package com.example.dagger_hilt.ui.activity.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dagger_hilt.R;
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final List<MovieEntity> movieEntities;
    private final MovieInterface movieInterface;

    public MovieAdapter(List<MovieEntity> movieEntities, MovieInterface movieInterface) {
        this.movieEntities = movieEntities;
        this.movieInterface = movieInterface;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  MovieViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false),
                movieInterface
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movieEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return movieEntities.size();
    }
}
