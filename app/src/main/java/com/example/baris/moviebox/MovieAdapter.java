package com.example.baris.moviebox;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by baris on 08.01.2017.
 */

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{
    Movie[] movieList;
    OnMovieClickHandler onMovieClickHandler;

    public MovieAdapter(OnMovieClickHandler _onMovieClickedInterface){
        onMovieClickHandler = _onMovieClickedInterface ;
    }

    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.gridview_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieAdapterViewHolder holder, int position) {
        Movie movie = movieList[position];
        //with Pıcasso, set the image of above movie to holder's imageView
        Picasso.with(holder.mImageView.getContext()).load(movie.posterImageLink).into(holder.mImageView);
        //holder.mImageView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
       return movieList == null ?  0 : movieList.length ;
    }

    /**
     * Sets the data list
     *
     * @param newList The new list to be assigned
     */
    public void setMovieList(Movie[] newList){
        movieList = newList ;
        notifyDataSetChanged();
    }

    /**
     * Custom ViewHolder that stores the references to the child views of the item view
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView mImageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_poster_recycler_item);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            onMovieClickHandler.onMovieClick(movieList[adapterPosition]);
        }
    }

    public interface OnMovieClickHandler{
        public void onMovieClick(Movie movie);
    }
}