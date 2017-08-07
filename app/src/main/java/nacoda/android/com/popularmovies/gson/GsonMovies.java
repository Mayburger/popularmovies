package nacoda.android.com.popularmovies.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mayburger on 6/15/2017.
 */

public class GsonMovies {

    @SerializedName("results")
    public List<GsonMovies.Data> results;

    public class Data {
        @SerializedName("poster_path")
        public String poster_path;
        @SerializedName("backdrop_path")
        public String backdrop_path;
        @SerializedName("title")
        public String title;
        @SerializedName("release_date")
        public String release_date;
        @SerializedName("overview")
        public String overview;
        @SerializedName("vote_average")
        public float vote_average;
        @SerializedName("vote_count")
        public int vote_count;
        @SerializedName("id")
        public String id;
    }



}
