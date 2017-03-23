package com.nnataraj.assignment8;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * Updates Movie List
 * Created by nagaprasad on 3/14/17.
 */
class UpdateMovieList extends AsyncTask<String, Void, Boolean> {

    private final WeakReference<MyMovieItemRecyclerViewAdapter> movieItemRecyclerViewAdapterReference;

    UpdateMovieList(final MyMovieItemRecyclerViewAdapter movieItemRecyclerViewAdapter) {
        movieItemRecyclerViewAdapterReference = new WeakReference<>(movieItemRecyclerViewAdapter);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return MovieContent.updateITEMS(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        final MyMovieItemRecyclerViewAdapter movieItemRecyclerViewAdapter;

        if (success
                && ((movieItemRecyclerViewAdapter = movieItemRecyclerViewAdapterReference.get()) != null)) {
            movieItemRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}
