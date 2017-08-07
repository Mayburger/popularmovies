package nacoda.android.com.popularmovies.asynctask;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import nacoda.android.com.popularmovies.DetailActivity;
import nacoda.android.com.popularmovies.ReviewsActivity;
import nacoda.android.com.popularmovies.adapter.ReviewsAdapter;
import nacoda.android.com.popularmovies.favoritesdata.FavoritesContract;
import nacoda.android.com.popularmovies.gson.GsonReviews;
import nacoda.android.com.popularmovies.parcelable.ParcelableMovies;
import nacoda.android.com.popularmovies.parcelable.ParcelableReviews;
import nacoda.android.com.popularmovies.utilities.NetworkUtils;

/**
 * Created by Mayburger on 7/27/2017.
 */

public class ReviewsAsyncTask extends AsyncTask<URL, Void, String> {

    private GsonReviews gsonReviews;
    private Context context;
    private Button btnReviews;

    public ReviewsAsyncTask(GsonReviews gsonReviews, Context context, Button btnReviews) {
        this.gsonReviews = gsonReviews;
        this.context = context;
        this.btnReviews = btnReviews;
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
        gsonReviews = gson.fromJson(s, GsonReviews.class);
        watchTrailersIntent(btnReviews);

    }

    public void watchTrailersIntent(Button btnReviews) {

        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reviewsIntent = new Intent(context, ReviewsActivity.class);

                for (int i = 0; i < gsonReviews.results.size(); i++) {

                    ParcelableReviews parcelableReviews = new ParcelableReviews(
                            gsonReviews.results.get(i).id,
                            gsonReviews.results.get(i).author,
                            gsonReviews.results.get(i).content,
                            gsonReviews.results.get(i).url
                    );
                    reviewsIntent.putExtra("parcelableReviews", parcelableReviews);
                }
                /** Sending data using putExtra **/


                /** Starting activity using startActivity method **/
                context.startActivity(reviewsIntent);
            }
        });
    }
}
