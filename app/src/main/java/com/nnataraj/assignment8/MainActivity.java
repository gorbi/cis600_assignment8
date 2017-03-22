package com.nnataraj.assignment8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nnataraj.assignment8.MovieContent.MovieItem;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MovieItemFragment.OnListFragmentInteractionListener {

    public static final String MovieServerURL = "http://tyrant.local";
    public static final String EntireMovieList = "/movies/";
    public static final String MovieIDURLPrefix = "/movies/id/";
    public static final String MovieRatingURLPrefix = "/movies/rating/";
    public static final String MovieAddSuffix = "/add";

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

    @Override
    public void onMenuClick(View view, final MovieItem movieItem) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.popup,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                try {
                    switch (menuItem.getItemId()) {
                        case R.id.duplicate:
                            //Log.e("NAGA", "Duplicate " + movieItem.details.getString("name"));
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String res = MyUtility.sendHttPostRequest(MovieServerURL+MovieAddSuffix,"id="+movieItem.details.getString("id")+"_new"
                                                +"&name="+movieItem.details.getString("name")
                                                +"&description="+movieItem.details.getString("description")
                                                +"&stars="+movieItem.details.getString("stars")
                                                +"&length="+movieItem.details.getString("length")
                                                +"&image="
                                                +"&year="+movieItem.details.getString("year")
                                                +"&rating="+movieItem.details.getString("rating")
                                                +"&director="+movieItem.details.getString("director")
                                                +"&url="+movieItem.details.getString("url"));

                                        JSONObject result = new JSONObject(res);
                                        if (result.getInt("affected_rows")>0)
                                            Toast.makeText(MainActivity.this, "Successfully duplicated: "+movieItem.details.getString("name"), Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(MainActivity.this, "Unable to duplicate: "+movieItem.details.getString("name"), Toast.LENGTH_SHORT).show();

                                        //Log.e("NAGA", "Duplication of " + movieItem.details.getString("name")+" done");
                                    }
                                    catch (JSONException ae) {
                                        //do nothing
                                        Toast.makeText(MainActivity.this, "Unable to duplicate", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            new Thread(runnable).start();

                            return true;
                        case R.id.delete:
                            Log.e("NAGA", "Delete " + movieItem.details.getString("name"));
                            return true;
                    }
                } catch (JSONException ae) {
                    //do nothing
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
