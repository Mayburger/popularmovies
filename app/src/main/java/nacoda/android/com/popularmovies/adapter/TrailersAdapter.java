package nacoda.android.com.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nacoda.android.com.popularmovies.R;
import nacoda.android.com.popularmovies.gson.GsonTrailers;

/**
 * Created by Mayburger on 4/19/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    public TrailersAdapter(List<GsonTrailers.Data> trailersData) {
        this.trailersData = trailersData;
    }

    private List<GsonTrailers.Data> trailersData;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trailers, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

    holder.tvTrailers.setText(trailersData.get(position).name);
    }

    @Override
    public int getItemCount() {
        return trailersData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTrailers;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTrailers = (TextView)itemView.findViewById(R.id.tvTrailers);
        }
    }
}
