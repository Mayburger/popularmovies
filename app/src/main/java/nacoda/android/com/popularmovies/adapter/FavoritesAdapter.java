package nacoda.android.com.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nacoda.android.com.popularmovies.DetailActivity;
import nacoda.android.com.popularmovies.FavoritesActivity;
import nacoda.android.com.popularmovies.R;
import nacoda.android.com.popularmovies.favoritesdata.FavoritesContract;
import nacoda.android.com.popularmovies.parcelable.ParcelableMovies;
import nacoda.android.com.popularmovies.utilities.NetworkUtils;


/**
 * This FavoritesAdapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.TaskViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    private RecyclerView rvFavorites;
    private int indexId, posterPathIndex, titleIndex, overviewIndex, releaseDateIndex,
            backdropPathIndex, voteAverageIndex, voteCountIndex, movieIdIndex;
    private String activityId = "FavoritesActivity";

    public FavoritesAdapter(Context mContext, RecyclerView rvFavorites) {
        this.mContext = mContext;
        this.rvFavorites = rvFavorites;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_favorites, parent, false);

        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        indexId = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry._ID);
        posterPathIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH);
        titleIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_TITLE);
        overviewIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW);
        releaseDateIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE);
        backdropPathIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_BACKDROP_PATH);
        voteAverageIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_VOTE_AVERAGE);
        voteCountIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT);
        movieIdIndex = mCursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID);

        mCursor.moveToPosition(position);

        String poster_path = mCursor.getString(posterPathIndex);
        String title = mCursor.getString(titleIndex);
        String overview = mCursor.getString(overviewIndex);
        String release_date = mCursor.getString(releaseDateIndex);
        final int id = mCursor.getInt(indexId);

        Typeface Roboto = Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_regular.ttf");
        Typeface RobotoBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_bold.ttf");

        Glide.with(mContext).load(NetworkUtils.IMAGE_URL + poster_path)
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.error_poster)
                .into(holder.ivListPoster);


        holder.tvListTitle.setTypeface(RobotoBold);
        holder.tvListTitle.setText(title.replace(": ", " :\n"));
        holder.itemView.setTag(id);


        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            holder.tvListOverview.setText(overview);
            holder.tvListReleaseDate.setText(release_date);

            holder.tvListOverview.setTypeface(Roboto);
            holder.tvListReleaseDate.setTypeface(Roboto);
        }

        recyclerOnClick();

    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder {

        ImageView ivListPoster;
        TextView tvListTitle, tvListReleaseDate, tvListOverview;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);
            ivListPoster = (ImageView) itemView.findViewById(R.id.ivMovies);
            tvListTitle = (TextView) itemView.findViewById(R.id.tvMovies);
            tvListReleaseDate = (TextView) itemView.findViewById(R.id.tvMoviesReleaseDate);
            tvListOverview = (TextView) itemView.findViewById(R.id.tvListOverview);

        }
    }

    private void recyclerOnClick() {
        rvFavorites.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {

                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);

                    Intent detailIntent = new Intent(mContext, DetailActivity.class);

                    ParcelableMovies parcelableMovies = new ParcelableMovies(
                            mCursor.getString(posterPathIndex),
                            mCursor.getString(backdropPathIndex),
                            mCursor.getString(titleIndex),
                            mCursor.getString(releaseDateIndex),
                            mCursor.getString(overviewIndex),
                            mCursor.getFloat(voteAverageIndex),
                            mCursor.getInt(voteCountIndex),
                            mCursor.getString(indexId)
                    );
                    /** Sending data using putExtra **/
                    detailIntent.putExtra("parcelableMovies", parcelableMovies);
                    detailIntent.putExtra("activityId", activityId);

                    /** Starting activity using startActivity method **/
                    mContext.startActivity(detailIntent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }
}