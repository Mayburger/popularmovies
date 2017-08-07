package nacoda.android.com.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import nacoda.android.com.popularmovies.adapter.ReviewsAdapter;
import nacoda.android.com.popularmovies.asynctask.ReviewsAsyncTask;
import nacoda.android.com.popularmovies.gson.GsonReviews;
import nacoda.android.com.popularmovies.parcelable.ParcelableReviews;
import nacoda.android.com.popularmovies.utilities.NetworkUtils;

public class ReviewsActivity extends AppCompatActivity {

    GsonReviews gsonReviews;
    @InjectView(R.id.lvTrailers)
    ListView lvReviews;
    Toolbar mActionBarToolbar;
    ParcelableReviews parcelableReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.inject(this);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Reviews");

        parcelableReviews = getIntent().getParcelableExtra("parcelableReviews");

        ReviewsAdapter mAdapter = new ReviewsAdapter(ReviewsActivity.this, parcelableReviews);
        lvReviews.setAdapter(mAdapter);



    }
}
