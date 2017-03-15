package com.nnataraj.assignment8;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nnataraj.assignment8.MovieItemFragment.OnListFragmentInteractionListener;
import com.nnataraj.assignment8.MovieContent.MovieItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMovieItemRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieItemRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;

    public MyMovieItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movieitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = MovieContent.ITEMS.get(position);
        try {
            holder.mTitle.setText(holder.mItem.details.getString("name"));
            holder.mDescription.setText(holder.mItem.details.getString("description"));
            holder.mIcon.setImageBitmap(holder.mItem.image);
            holder.mRatingBar.setRating(((float) holder.mItem.details.getDouble("rating")) / 2f);
            holder.mYear.setText(holder.mItem.details.getString("year"));
        } catch (Exception ae) {
            //Ignore
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return MovieContent.ITEMS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mDescription;
        public final ImageView mIcon;
        public final TextView mYear;
        public final RatingBar mRatingBar;
        public MovieItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.title);
            mDescription = (TextView) view.findViewById(R.id.description);
            mIcon = (ImageView) view.findViewById(R.id.movie_icon);
            mYear = (TextView) view.findViewById(R.id.year);
            mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        }
    }
}
