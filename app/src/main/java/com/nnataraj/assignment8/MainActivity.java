package com.nnataraj.assignment8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nnataraj.assignment8.MovieContent.MovieItem;

public class MainActivity extends AppCompatActivity implements MovieItemFragment.OnListFragmentInteractionListener {

    public static final String MovieServerURL = "http://tyrant.local";
    public static final String EntireMovieList = "/movies/";
    public static final String MovieIDURLPrefix = "/movies/id/";

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(MovieItem item) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, MovieItemDetailFragment.newInstance(MovieServerURL + MovieIDURLPrefix + item.details.getString("id")))
                    .commit();
        } catch (Exception ae) {
            //Ignore
        }
    }
}
