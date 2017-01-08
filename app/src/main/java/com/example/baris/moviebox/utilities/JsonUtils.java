package com.example.baris.moviebox.utilities;

import com.example.baris.moviebox.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by baris on 08.01.2017.
 */

public class JsonUtils {

    private static final String JSON_ARRAY_TAG = "results" ;
    private static final String TITLE_TAG = "original_title";
    private static final String POSTER_TAG = "poster_path";
    private static final String SYNOPSIS_TAG = "overview";
    private static final String RATING_TAG =  "vote_average";
    private static final String RELEASE_TAG= "release_date";

    public static Movie[] getMovieListFromJsonString(String string) throws JSONException {
        if(string == null){
            return null ;
        }else if(string.equals("")){
            //empty list
            return new Movie[0];
        }
        JSONObject jsonObject = new JSONObject(string) ;
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        JSONArray movieArray = jsonObject.getJSONArray(JSON_ARRAY_TAG);
        for (int i = 0 ; i < movieArray.length() ; i++){
            JSONObject movieJsonObject = movieArray.getJSONObject(i);
            String title = movieJsonObject.getString(TITLE_TAG);
            String posterImage = movieJsonObject.getString(POSTER_TAG);
            String synopsis  = movieJsonObject.getString(SYNOPSIS_TAG);
            String rating = movieJsonObject.getString(RATING_TAG);
            String releaseDate = movieJsonObject.getString(RELEASE_TAG);
            arrayList.add(new Movie(title, posterImage, synopsis, rating, releaseDate));
        }

        return arrayList.toArray(new Movie[arrayList.size()]);

    }
}
