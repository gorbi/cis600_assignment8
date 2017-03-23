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
class MyMovieItemRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieItemRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mFListener;
    private OnListInteractionListener mListener;

    MyMovieItemRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mFListener = listener;
    }

    void setOnListInteractionListener(OnListInteractionListener mListener) {
        this.mListener = mListener;
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
        final int pos = position;
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
                    mFListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        final ImageView menuOverflow = (ImageView) holder.mView.findViewById(R.id.menu_moreoverflow);

        menuOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOverflowMenuClick(v, holder.mItem, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MovieContent.ITEMS.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mTitle;
        final TextView mDescription;
        final ImageView mIcon;
        final TextView mYear;
        final RatingBar mRatingBar;
        MovieItem mItem;

        ViewHolder(View view) {
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
