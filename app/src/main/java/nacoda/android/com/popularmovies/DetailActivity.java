package nacoda.android.com.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nacoda.android.com.popularmovies.asynctask.ReviewsAsyncTask;
import nacoda.android.com.popularmovies.asynctask.TrailerAsyncTask;
import nacoda.android.com.popularmovies.favoritesdata.FavoritesContract;
import nacoda.android.com.popularmovies.gson.GsonReviews;
import nacoda.android.com.popularmovies.gson.GsonTrailers;
import nacoda.android.com.popularmovies.parcelable.ParcelableMovies;
import nacoda.android.com.popularmovies.utilities.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    @InjectView(R.id.ivBackdrop)
    ImageView ivBackdrop;
    @InjectView(R.id.ivPoster)
    ImageView ivPoster;
    @InjectView(R.id.tvTitle)
    TextView tvTitle;
    @InjectView(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    @InjectView(R.id.tvRating)
    TextView tvRating;
    @InjectView(R.id.rbMovie)
    RatingBar rbMovie;
    @InjectView(R.id.tvOverview)
    TextView tvOverview;
    @InjectView(R.id.tvVotes)
    TextView tvVotes;
    @InjectView(R.id.rvReviews)
    RecyclerView rvReviews;
    @InjectView(R.id.btnReviews)
    Button btnReviews;
    GsonTrailers gsonTrailers;
    GsonReviews gsonReviews;
    ParcelableMovies parcelableMovies;
    String activityId;

    ImageView ivFavorite;
    ImageView ivUnfavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityId = getIntent().getStringExtra("activityId");

        if (activityId.equals("FavoritesActivity")) {
            setContentView(R.layout.activity_detail_favorites);
        } else if (activityId.equals("MoviesActivity")) {
            setContentView(R.layout.activity_detail);
            ivFavorite = (ImageView)findViewById(R.id.ivFavorite);
            ivUnfavorite = (ImageView)findViewById(R.id.ivUnfavorite);
        }


        /** Fonts **/
        Typeface Roboto = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
        Typeface RobotoBold = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");

        ButterKnife.inject(this);

        /** Getting data using intent parceable **/
        parcelableMovies = getIntent().getParcelableExtra("parcelableMovies");

        /** Setting the data into the holders **/
        // Settings ImageView holders background using Glide
        methodGlide(parcelableMovies.getBackdrop_path(), ivBackdrop);
        methodGlide(parcelableMovies.getPoster_path(), ivPoster);

        // Setting TextView holders
        methodSetText(tvTitle, parcelableMovies.getTitle(), RobotoBold);
        methodSetText(tvReleaseDate, parcelableMovies.getRelease_date(), Roboto);
        methodSetText(tvRating, String.valueOf(parcelableMovies.getVote_average()), RobotoBold);
        methodSetText(tvVotes, String.valueOf(parcelableMovies.getVote_count()).replace(".0", "") + " votes on TMDB", Roboto);
        methodSetText(tvOverview, parcelableMovies.getOverview(), Roboto);

        // Setting RatingBar holders
        rbMovie.setRating(parcelableMovies.getVote_average() / 2);

        // Setting tvTitle data when screenOrientation is landscaped
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            tvTitle.setText(parcelableMovies.getTitle().replace(": ", " :\n"));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvReviews.setLayoutManager(linearLayoutManager);

        getData();

        if (activityId.equals("MoviesActivity")) {
            favoritesController();
        }

    }

    private void methodGlide(String path, final View ivData) {
        Glide.with(getApplicationContext()).load(NetworkUtils.IMAGE_URL + path).asBitmap().into(new SimpleTarget<Bitmap>(200, 200) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ivData.setBackground(drawable);
                }
            }
        });
    }

    private void methodSetText(TextView tvData, String txtData, Typeface tfData) {
        tvData.setText(txtData);
        tvData.setTypeface(tfData);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (activityId.equals("FavoritesActivity")) {
            startActivity(new Intent(getBaseContext(), FavoritesActivity.class));
        } else if (activityId.equals("MoviesActivity")) {
            startActivity(new Intent(getBaseContext(), MoviesActivity.class));
        }

        finish();

    }

    private void getData() {

        String mMovieTrailer = parcelableMovies.getId();
        URL reviewsUrl = NetworkUtils.trailersUrl(mMovieTrailer);
        new TrailerAsyncTask(
                rvReviews,
                gsonTrailers,
                DetailActivity.this)
                .execute(reviewsUrl);

        URL trailersUrl = NetworkUtils.reviewsUrl(mMovieTrailer);
        new ReviewsAsyncTask(
                gsonReviews,
                DetailActivity.this,
                btnReviews)
                .execute(trailersUrl);
    }

    public void favoritesController() {

        String projection[] = {FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID};

        Cursor cursor = getContentResolver().query(FavoritesContract.FavoritesEntry.CONTENT_URI,
                projection,
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + " = " + parcelableMovies.getId(),
                null,
                null);

        if (cursor.getCount() == 0) {
            ivFavorite.setVisibility(View.VISIBLE);
            ivUnfavorite.setVisibility(View.GONE);
        } else {
            while (cursor.moveToNext()) {
                String movieId = cursor.getString(0);
                if (movieId.equals(parcelableMovies.getId())) {
                    ivUnfavorite.setVisibility(View.VISIBLE);
                    ivFavorite.setVisibility(View.GONE);
                }
            }
        }

        cursor.close();


    }

    public void addData(View view) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH, parcelableMovies.getPoster_path());
        contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_BACKDROP_PATH, parcelableMovies.getBackdrop_path());
        contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_TITLE, parcelableMovies.getTitle());
        contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_RELEASE_DATE, parcelableMovies.getRelease_date());
        contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW, parcelableMovies.getOverview());
        contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_VOTE_AVERAGE, parcelableMovies.getVote_average());
        contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT, parcelableMovies.getVote_count());
        contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, parcelableMovies.getId());

        getContentResolver().insert(FavoritesContract.FavoritesEntry.CONTENT_URI, contentValues);
        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();

        ivUnfavorite.setVisibility(View.VISIBLE);
        ivFavorite.setVisibility(View.GONE);

    }

    public void deleteData(View view) {

        ContentResolver contentResolver = getContentResolver();

        Uri uri = FavoritesContract.FavoritesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(parcelableMovies.getId()).build();

        contentResolver.delete(uri,
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + " = " + parcelableMovies.getId(),
                null);
        Toast.makeText(this, "Unfavorited", Toast.LENGTH_SHORT).show();

        ivUnfavorite.setVisibility(View.GONE);
        ivFavorite.setVisibility(View.VISIBLE);
    }

}
