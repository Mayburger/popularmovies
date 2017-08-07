package nacoda.android.com.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nacoda.android.com.popularmovies.R;
import nacoda.android.com.popularmovies.gson.GsonReviews;
import nacoda.android.com.popularmovies.parcelable.ParcelableReviews;

/**
 * Created by Mayburger on 4/19/2017.
 */

public class ReviewsAdapter extends BaseAdapter {

    Context context;
    private ParcelableReviews parcelableReviews;

    public ReviewsAdapter(Context context, ParcelableReviews parcelableReviews) {
        this.context = context;
        this.parcelableReviews = parcelableReviews;
    }


    @Override
    public int getCount() {
        return parcelableReviews.getAuthor().length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.list_reviews, null);

        TextView tvReviewsAuthor = (TextView) v.findViewById(R.id.tvReviewsAuthor);
        TextView tvReviewsContent = (TextView) v.findViewById(R.id.tvReviewsContent);

        tvReviewsAuthor.setText(parcelableReviews.getAuthor());
        tvReviewsContent.setText(parcelableReviews.getContent());

        return v;
    }

}
