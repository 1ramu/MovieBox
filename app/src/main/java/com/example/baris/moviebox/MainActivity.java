package com.example.baris.moviebox;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.baris.moviebox.utilities.JsonUtils;
import com.example.baris.moviebox.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickHandler{
    private String TAG = "MainActivity";

    private RecyclerView recyclerView ;
    private ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_loading_movie_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2 , GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        MovieAdapter movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

        recyclerView.setHasFixedSize(true);

        loadMovies(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_highest_rated){
            loadMovies(false);
        }else if(item.getItemId() == R.id.menu_most_popular){
            loadMovies(true);
        }
        return true;
    }

    public void loadMovies(boolean popular){
        new FetchMoviesAsync().execute(popular);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this , MovieDetails.class);
        Bundle bundle = new Bundle();
        bundle.putString("title" , movie.title);
        bundle.putString("posterImageLink" , movie.posterImageLink);
        bundle.putString("synopsis" , movie.synopsis);
        bundle.putString("rating", movie.rating);
        bundle.putString("releaseDate" , movie.releaseDate);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public class FetchMoviesAsync extends AsyncTask<Boolean, Void, Movie[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Movie[] doInBackground(Boolean... params) {
            boolean popular = params[0].booleanValue();
            String response = null ;
            try {
                response = NetworkUtils.getResponseFromHTTP(NetworkUtils.buildURL(popular));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response != null){
                try {
                    return JsonUtils.getMovieListFromJsonString(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null ;

        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            if(movies != null){
                MovieAdapter movieAdapter = (MovieAdapter) recyclerView.getAdapter();
                movieAdapter.setMovieList(movies);
            }
        }
    }
}
