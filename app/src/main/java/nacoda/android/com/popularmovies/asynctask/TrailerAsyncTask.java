package nacoda.android.com.popularmovies.asynctask;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;

import nacoda.android.com.popularmovies.DetailActivity;
import nacoda.android.com.popularmovies.adapter.TrailersAdapter;
import nacoda.android.com.popularmovies.favoritesdata.FavoritesContract;
import nacoda.android.com.popularmovies.gson.GsonTrailers;
import nacoda.android.com.popularmovies.utilities.NetworkUtils;

/**
 * Created by Mayburger on 7/27/2017.
 */

public class TrailerAsyncTask extends AsyncTask<URL, Void, String> {

    private RecyclerView rvTrailers;
    private GsonTrailers gsonTrailers;
    private Context context;

    public TrailerAsyncTask(RecyclerView rvTrailers, GsonTrailers gsonTrailers, Context context) {
        this.rvTrailers = rvTrailers;
        this.gsonTrailers = gsonTrailers;
        this.context = context;
    }

    @Override
    protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
        String popularMoviesResult = null;
        try {
            popularMoviesResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return popularMoviesResult;
    }

    @Override
    protected void onPostExecute(String s) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        gsonTrailers = gson.fromJson(s, GsonTrailers.class);

        if (s != null && !s.equals("")) {
            TrailersAdapter adapter = new TrailersAdapter(gsonTrailers.results);
            rvTrailers.setAdapter(adapter);
            onDataRetrieved();
        }
    }

    /**
     * Actions when data is retrieved from the internet
     */
    private void onDataRetrieved() {
        rvTrailers.setVisibility(View.VISIBLE);
        recyclerOnClick();
    }


    private void recyclerOnClick() {
        rvTrailers.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);

                    String mUrlString = "https://www.youtube.com/watch?v=" + gsonTrailers.results.get(position).key;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mUrlString));
                    context.startActivity(intent);

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
