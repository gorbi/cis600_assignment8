package com.nnataraj.assignment8;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import com.nnataraj.assignment8.MovieContent.MovieItem;

/**
 * Deletion of Movie
 * Created by nagaprasad on 3/14/17.
 */
class UpdateMovieDetail extends AsyncTask<String, Void, MovieItem> {

    private final WeakReference<View> rootViewReference;

    UpdateMovieDetail(final View rootView) {
        rootViewReference = new WeakReference<>(rootView);
    }

    @Override
    protected MovieItem doInBackground(String... params) {
        try {
            MovieItem movieItem = new MovieItem();
            movieItem.details = new JSONObject(MyUtility.downloadJSONusingHTTPGetRequest(params[0]));
            movieItem.image = MyUtility.downloadImageusingHTTPGetRequest(movieItem.details.getString("url"));
            return movieItem;
        } catch (Exception ae) {
            Log.d("UpdateMovieDetail", "Unable to fetch movie details from: " + params[0]);
            ae.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(MovieItem movieItem) {

        final View rootView;

        if (movieItem != null
                && ((rootView = rootViewReference.get()) != null)) {

            final TextView name = (TextView) rootView.findViewById(R.id.title);
            final TextView year = (TextView) rootView.findViewById(R.id.year);
            final TextView length = (TextView) rootView.findViewById(R.id.length);
            final RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
            final TextView director = (TextView) rootView.findViewById(R.id.director);
            final TextView cast = (TextView) rootView.findViewById(R.id.cast);
            final TextView description = (TextView) rootView.findViewById(R.id.description);
            final ImageView imageView = (ImageView) rootView.findViewById(R.id.image);

            try {
                name.setText(movieItem.details.getString("name"));
                year.setText(movieItem.details.getString("year"));
                length.setText(movieItem.details.getString("length"));
                director.setText(movieItem.details.getString("director"));
                cast.setText(movieItem.details.getString("stars"));
                description.setText(movieItem.details.getString("description"));
                ratingBar.setRating(((float) movieItem.details.getDouble("rating")) / 2f);
                imageView.setImageBitmap(movieItem.image);
            } catch (Exception ae) {
                Log.d("UpdateMovieDetail", "Unable to set movie details to view");
                ae.printStackTrace();
            }

        }
    }
}
