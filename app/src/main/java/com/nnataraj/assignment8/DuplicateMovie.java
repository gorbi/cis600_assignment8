package com.nnataraj.assignment8;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static com.nnataraj.assignment8.MainActivity.EntireMovieList;
import static com.nnataraj.assignment8.MainActivity.MovieAddSuffix;
import static com.nnataraj.assignment8.MainActivity.MovieServerURL;

/**
 * Duplication of Movie
 * Created by nagaprasad on 3/20/17.
 */

class DuplicateMovie extends AsyncTask<String, Void, Integer> {

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
            if (result.getInt("affected_rows") > 0) {
                if (!MovieContent.updateITEMS(MovieServerURL + EntireMovieList))
                    return -2;
                return Integer.parseInt(params[1]);
            } else
                return -1;
        } catch (Exception ae) {
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        final Context context;
        switch (integer) {
            case -1:
                if ((context = contextReference.get()) != null)
                    Toast.makeText(context, "Unable to duplicate movie: " + movieName, Toast.LENGTH_SHORT).show();
                break;
            case -2:
                if ((context = contextReference.get()) != null)
                    Toast.makeText(context, "Unable to refresh local content however duplication of movie " + movieName + " was successful, please reload the app", Toast.LENGTH_SHORT).show();
                break;
            default:
                final MyMovieItemRecyclerViewAdapter myMovieItemRecyclerViewAdapter;
                if ((myMovieItemRecyclerViewAdapter = myMovieItemRecyclerViewAdapterReference.get()) != null) {
                    myMovieItemRecyclerViewAdapter.notifyDataSetChanged();
                    //myMovieItemRecyclerViewAdapter.notifyItemInserted(integer+1);
                }
        }
    }
}
