package com.nnataraj.assignment8;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Local Movie Content
 * Created by nagaprasad on 3/14/17.
 */

class MovieContent {

    /**
     * An array of sample (movie) items.
     */
    static final List<MovieItem> ITEMS = new ArrayList<>();

    private static void updateITEMS(List<MovieItem> newItems) {
        ITEMS.clear();
        for (MovieItem item : newItems) {
            ITEMS.add(item);
        }
    }

    static boolean updateITEMS(String url) {
        try {
            JSONArray movieList = new JSONArray(MyUtility.downloadJSONusingHTTPGetRequest(url));
            List<MovieContent.MovieItem> resultMovieList = new ArrayList<>();
            for (int i = 0; i < movieList.length(); i++) {
                MovieContent.MovieItem movieItem = new MovieContent.MovieItem();
                movieItem.details = movieList.getJSONObject(i);
                movieItem.image = MyUtility.downloadImageusingHTTPGetRequest(movieItem.details.getString("url"));
                resultMovieList.add(movieItem);
            }
            MovieContent.updateITEMS(resultMovieList);
            return true;
        } catch (Exception ae) {
            Log.d("MovieContent", "Unable to update movie list from: " + url);
            ae.printStackTrace();
            return false;
        }
    }

    /**
     * A Movie item representing a piece of content.
     */
    static class MovieItem {
        JSONObject details;
        public Bitmap image;

        @Override
        public String toString() {
            try {
                return details.getString("id");
            } catch (Exception ae) {
                return null;
            }
        }
    }
}
