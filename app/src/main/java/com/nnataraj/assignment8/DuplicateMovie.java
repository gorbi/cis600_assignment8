package com.nnataraj.assignment8;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static com.nnataraj.assignment8.MainActivity.MovieAddSuffix;
import static com.nnataraj.assignment8.MainActivity.MovieServerURL;

/**
 * Created by nagaprasad on 3/20/17.
 */

public class DuplicateMovie extends AsyncTask<String, Void, Integer> {

    private final WeakReference<Context> contextReference;
    private final WeakReference<MyMovieItemRecyclerViewAdapter> myMovieItemRecyclerViewAdapterReference;
    private final String movieName;

    DuplicateMovie(Context context, MyMovieItemRecyclerViewAdapter myMovieItemRecyclerViewAdapter, String movieName) {
        contextReference = new WeakReference<>(context);
        myMovieItemRecyclerViewAdapterReference = new WeakReference<>(myMovieItemRecyclerViewAdapter);
        this.movieName = movieName;
    }

    @Override
    protected Integer doInBackground(String... params) {
        //First parameter of params is assumed to be HTTP parameters which are needed for duplication operation
        //Second parameter of params is assumed to be position at which duplication is called
        try {
            String res = MyUtility.sendHttPostRequest(MovieServerURL + MovieAddSuffix, params[0]);

            JSONObject result = new JSONObject(res);
            if (result.getInt("affected_rows") > 0)
                return Integer.parseInt(params[1]);
            else
                return -1;
        } catch (Exception ae) {
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if (integer != -1) {
            final MyMovieItemRecyclerViewAdapter myMovieItemRecyclerViewAdapter;
            if ((myMovieItemRecyclerViewAdapter = myMovieItemRecyclerViewAdapterReference.get()) != null) {
                //myMovieItemRecyclerViewAdapter.notifyDataSetChanged();
                myMovieItemRecyclerViewAdapter.notifyItemInserted(integer);
            }
        } else {
            final Context context;
            if ((context = contextReference.get()) != null)
                Toast.makeText(context, "Unable to duplicate movie: " + movieName, Toast.LENGTH_SHORT).show();
        }
    }
}
