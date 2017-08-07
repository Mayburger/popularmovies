package nacoda.android.com.popularmovies.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mayburger on 7/28/2017.
 */

public class GsonReviews {

    @SerializedName("results")
    public List<Data> results;

    public class Data {
        @SerializedName("id")
        public String id;
        @SerializedName("author")
        public String author;
        @SerializedName("content")
        public String content;
        @SerializedName("url")
        public String url;
    }
}
