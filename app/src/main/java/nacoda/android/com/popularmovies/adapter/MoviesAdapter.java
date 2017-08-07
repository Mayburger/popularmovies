package nacoda.android.com.popularmovies.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nacoda.android.com.popularmovies.R;
import nacoda.android.com.popularmovies.gson.GsonMovies;
import nacoda.android.com.popularmovies.utilities.NetworkUtils;

/**
 * Created by Mayburger on 4/19/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private List<GsonMovies.Data> moviesData;




    public MoviesAdapter(Context context, List<GsonMovies.Data> moviesData) {
        this.context = context;
        this.moviesData = moviesData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movies, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        /**
         * Fonts
         **/

        Typeface Roboto = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf");
        Typeface RobotoBold = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_bold.ttf");

        Glide.with(context).load(NetworkUtils.IMAGE_URL + moviesData.get(position).poster_path)
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.error_poster)
                .into(holder.ivListPoster);

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            holder.tvListTitle.setText(moviesData.get(position).title.replace(": ", " :\n"));
            holder.tvListOverview.setText(moviesData.get(position).overview);
            holder.tvListReleaseDate.setText(moviesData.get(position).release_date);
            holder.tvListTitle.setTypeface(RobotoBold);
            holder.tvListOverview.setTypeface(Roboto);
            holder.tvListReleaseDate.setTypeface(Roboto);
        }
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivListPoster;
        TextView tvListTitle, tvListReleaseDate, tvListOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            ivListPoster = (ImageView) itemView.findViewById(R.id.ivMovies);
            tvListTitle = (TextView) itemView.findViewById(R.id.tvMovies);
            tvListReleaseDate = (TextView) itemView.findViewById(R.id.tvMoviesReleaseDate);
            tvListOverview = (TextView) itemView.findViewById(R.id.tvListOverview);


        }
    }
}
