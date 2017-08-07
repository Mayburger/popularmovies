package nacoda.android.com.popularmovies.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import nacoda.android.com.popularmovies.DetailActivity;
import nacoda.android.com.popularmovies.gson.GsonMovies;
import nacoda.android.com.popularmovies.parcelable.ParcelableMovies;

/**
 * Created by Mayburger on 8/2/2017.
 */

public class RecyclerHelper {
    public static RecyclerView.LayoutManager checkOrientation(Context mContext) {

        // LayoutManagers
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // LayoutManagers

        RecyclerView.LayoutManager mLayoutManager = gridLayoutManager;

        switch (mContext.getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                mLayoutManager = gridLayoutManager;
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                mLayoutManager = linearLayoutManager;
                break;
        }
        return mLayoutManager;
    }

    public static void recyclerOnClick(RecyclerView rvMovies, final Context mContext, final GsonMovies gsonMovies) {
        rvMovies.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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

                    String activityId = "MoviesActivity";

                    Intent detailIntent = new Intent(mContext, DetailActivity.class);

                    ParcelableMovies parcelableMovies = new ParcelableMovies(
                            gsonMovies.results.get(position).poster_path,
                            gsonMovies.results.get(position).backdrop_path,
                            gsonMovies.results.get(position).title,
                            gsonMovies.results.get(position).release_date,
                            gsonMovies.results.get(position).overview,
                            gsonMovies.results.get(position).vote_average,
                            gsonMovies.results.get(position).vote_count,
                            gsonMovies.results.get(position).id
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
