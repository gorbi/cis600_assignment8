package com.nnataraj.assignment8;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by nagaprasad on 3/14/17.
 */

public class UpdateMovieDetail extends AsyncTask<String, Void, Movie> {

    private final WeakReference<View> rootViewReference;

    UpdateMovieDetail(final View rootView) {
        rootViewReference = new WeakReference(rootView);
    }

    @Override
    protected Movie doInBackground(String... params) {
        try {
            Movie movie = new Movie();
            movie.data = new JSONObject(MyUtility.downloadJSONusingHTTPGetRequest(params[0]));
            movie.image = MyUtility.downloadImageusingHTTPGetRequest(movie.data.getString("url"));
            return movie;
        } catch (Exception ae) {
            Log.d("UpdateMovieDetail","Unable to fetch movie details from: "+params[0]);
            ae.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Movie movie) {

        final View rootView;

        if (movie != null
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
                name.setText(movie.data.getString("name"));
                year.setText(movie.data.getString("year"));
                length.setText(movie.data.getString("length"));
                director.setText(movie.data.getString("director"));
                cast.setText(movie.data.getString("stars"));
                description.setText(movie.data.getString("description"));
                ratingBar.setRating(((float) movie.data.getDouble("rating")) / 2f);
                imageView.setImageBitmap(movie.image);
            } catch (Exception ae) {
                Log.d("UpdateMovieDetail","Unable to set movie details to view");
                ae.printStackTrace();
            }

        }
    }
}
