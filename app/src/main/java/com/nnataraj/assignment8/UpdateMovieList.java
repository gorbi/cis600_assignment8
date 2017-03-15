package com.nnataraj.assignment8;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.nnataraj.assignment8.MovieContent.MovieItem;

/**
 * Created by nagaprasad on 3/14/17.
 */
public class UpdateMovieList extends AsyncTask<String, Void, Boolean> {

    private final WeakReference<MyMovieItemRecyclerViewAdapter> movieItemRecyclerViewAdapterReference;

    UpdateMovieList(final MyMovieItemRecyclerViewAdapter movieItemRecyclerViewAdapter) {
        movieItemRecyclerViewAdapterReference = new WeakReference(movieItemRecyclerViewAdapter);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        List<MovieItem> resultMovieList = new ArrayList<>();
        try {
            JSONArray movieList = new JSONArray(MyUtility.downloadJSONusingHTTPGetRequest(params[0]));
            for (int i = 0; i < movieList.length(); i++) {
                MovieItem movieItem = new MovieItem();
                movieItem.details = movieList.getJSONObject(i);
                movieItem.image = MyUtility.downloadImageusingHTTPGetRequest(movieItem.details.getString("url"));
                resultMovieList.add(movieItem);
            }
        } catch (Exception ae) {
            Log.d("UpdateMovieList", "Unable to fetch movie list from: " + params[0]);
            ae.printStackTrace();
            return false;
        }
        MovieContent.ITEMS = resultMovieList;
        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        final MyMovieItemRecyclerViewAdapter movieItemRecyclerViewAdapter;

        if (success == true
                && ((movieItemRecyclerViewAdapter = movieItemRecyclerViewAdapterReference.get()) != null)) {
            movieItemRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}