package com.nnataraj.assignment8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nnataraj.assignment8.MovieContent.MovieItem;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements MovieItemFragment.OnListFragmentInteractionListener {

    public static final String MovieServerURL = "http://tyrant.local";
    public static final String EntireMovieList = "/movies/";
    public static final String MovieIDURLPrefix = "/movies/id/";
    public static final String MovieRatingURLPrefix = "/movies/rating/";
    public static final String MovieAddSuffix = "/add";
    public static final String DeleteMoviePrefix = "/delete/id/";

    // String forrestGumpImageURL = "http://ia.media-imdb.com/images/M/MV5BYThjM2MwZGMtMzg3Ny00NGRkLWE4M2EtYTBiNWMzOTY0YTI4XkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_SX214_AL_.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new MovieItemFragment())
                .commit();
    }

    @Override
    public void onListFragmentInteraction(MovieItem item) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, MovieItemDetailFragment.newInstance(MovieServerURL + MovieIDURLPrefix + item.details.getString("id")))
                    .addToBackStack("store")
                    .commit();
        } catch (JSONException ae) {
            //Ignore
        }
    }

}
