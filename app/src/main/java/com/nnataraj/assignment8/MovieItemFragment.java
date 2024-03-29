package com.nnataraj.assignment8;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nnataraj.assignment8.MovieContent.MovieItem;

import org.json.JSONException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieItemFragment extends Fragment {

    private static boolean refreshMovies = false;

    private OnListFragmentInteractionListener mListener;
    private MyMovieItemRecyclerViewAdapter myMovieItemRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieItemFragment() {
    }

    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = 20;
            }
            outRect.bottom = 20;
            outRect.left = 20;
            outRect.right = 20;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.action_search) == null)
            inflater.inflate(R.menu.menu_main, menu);
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (refreshMovies) {
            MovieContent.ITEMS.clear();
            new UpdateMovieList(myMovieItemRecyclerViewAdapter).execute(MainActivity.MovieServerURL + MainActivity.EntireMovieList);
            refreshMovies = false;
        }

        if (search != null) {

            search.setQueryHint("Rating...");

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    new UpdateMovieList(myMovieItemRecyclerViewAdapter).execute(MainActivity.MovieServerURL + MainActivity.MovieRatingURLPrefix + query);
                    refreshMovies = true;
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    return true;
                }

            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movieitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            myMovieItemRecyclerViewAdapter = new MyMovieItemRecyclerViewAdapter(mListener);
            recyclerView.setAdapter(myMovieItemRecyclerViewAdapter);
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration());
            myMovieItemRecyclerViewAdapter.setOnListInteractionListener(new OnListInteractionListener() {
                @Override
                public void onOverflowMenuClick(View view, final MovieItem movieItem, final int position) {
                    PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            try {
                                switch (menuItem.getItemId()) {
                                    case R.id.duplicate:
                                        Log.d("NAGA", "Duplicate " + movieItem.details.getString("id"));
                                        new DuplicateMovie(getActivity(), myMovieItemRecyclerViewAdapter, movieItem.details.getString("name"))
                                                .execute("id=" + movieItem.details.getString("id") + "_new"
                                                        + "&name=" + movieItem.details.getString("name")
                                                        + "&description=" + movieItem.details.getString("description")
                                                        + "&stars=" + movieItem.details.getString("stars")
                                                        + "&length=" + movieItem.details.getString("length")
                                                        + "&image=" + movieItem.details.getString("image")
                                                        + "&year=" + movieItem.details.getString("year")
                                                        + "&rating=" + movieItem.details.getString("rating")
                                                        + "&director=" + movieItem.details.getString("director")
                                                        + "&url=" + movieItem.details.getString("url"), String.valueOf(position));
                                        return true;
                                    case R.id.delete:
                                        Log.d("NAGA", "Delete " + movieItem.details.getString("id"));
                                        new DeleteMovie(getActivity(), myMovieItemRecyclerViewAdapter, movieItem.details.getString("name"))
                                                .execute(MainActivity.MovieServerURL + MainActivity.DeleteMoviePrefix + movieItem.details.getString("id")
                                                        , String.valueOf(position));
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
            });
            new UpdateMovieList(myMovieItemRecyclerViewAdapter).execute(MainActivity.MovieServerURL + MainActivity.EntireMovieList);
        }
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MovieItem item);
    }
}
