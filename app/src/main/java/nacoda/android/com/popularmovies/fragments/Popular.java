package nacoda.android.com.popularmovies.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import nacoda.android.com.popularmovies.adapter.MoviesAdapter;
import nacoda.android.com.popularmovies.R;
import nacoda.android.com.popularmovies.utilities.GridSpacingItemDecoration;
import nacoda.android.com.popularmovies.gson.GsonMovies;
import nacoda.android.com.popularmovies.utilities.NetworkUtils;
import nacoda.android.com.popularmovies.utilities.RecyclerHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class Popular extends Fragment {


    GsonMovies gsonMovies;
    SwipeRefreshLayout swipeRefreshMovie;
    RecyclerView rvMovies;
    TextView tvError;
    ImageView ivError;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    public Popular() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movies, container, false);

        rvMovies = (RecyclerView) v.findViewById(R.id.rvMovies);
        swipeRefreshMovie = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshMovie);
        tvError = (TextView) v.findViewById(R.id.tvError);
        ivError = (ImageView) v.findViewById(R.id.ivError);

        requestQueue = Volley.newRequestQueue(getActivity());

        // GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        // SwipeRefreshLayout color
        swipeRefreshMovie.setColorSchemeResources(R.color.colorPrimary);

        // LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvMovies.setLayoutManager(gridLayoutManager);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvMovies.setLayoutManager(linearLayoutManager);
        }

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        rvMovies.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));

        swipeRefreshMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData() {
        rvMovies.setVisibility(View.GONE);
        swipeRefreshMovie.setRefreshing(true);

        stringRequest = new StringRequest(Request.Method.GET, NetworkUtils.popularUrl().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                gsonMovies = gson.fromJson(response, GsonMovies.class);

                onSuccess();

                MoviesAdapter adapter = new MoviesAdapter(getActivity(), gsonMovies.results);
                rvMovies.setLayoutManager(RecyclerHelper.checkOrientation(getActivity()));
                RecyclerHelper.recyclerOnClick(rvMovies, getActivity(), gsonMovies);
                rvMovies.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "No internet!", Toast.LENGTH_SHORT).show();
                onError();
            }
        });

        requestQueue.add(stringRequest);
    }

    private void onError() {
        ivError.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        swipeRefreshMovie.setRefreshing(false);
    }

    private void onSuccess() {
        rvMovies.setVisibility(View.VISIBLE);
        ivError.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        swipeRefreshMovie.setRefreshing(false);
    }

}


