package com.nnataraj.assignment8;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieItemDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieItemDetailFragment extends Fragment {
    private static final String ARG_MOVIEIDURL = "movieIDURL";

    private String mMovieIDURL;

    public MovieItemDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param movieIDURL Parameter 1.
     * @return A new instance of fragment MovieItemDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieItemDetailFragment newInstance(String movieIDURL) {
        MovieItemDetailFragment fragment = new MovieItemDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIEIDURL, movieIDURL);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieIDURL = getArguments().getString(ARG_MOVIEIDURL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_item_detail, container, false);

        new UpdateMovieDetail(rootView).execute(mMovieIDURL);

        return rootView;
    }

}
