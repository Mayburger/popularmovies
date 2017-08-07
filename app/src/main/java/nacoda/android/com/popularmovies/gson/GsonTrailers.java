package nacoda.android.com.popularmovies.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mayburger on 7/27/2017.
 */

public class GsonTrailers {

    @SerializedName("results")
    public List<GsonTrailers.Data> results;

    public class Data {
        @SerializedName("id")
        public String id;
        @SerializedName("key")
        public String key;
        @SerializedName("name")
        public String name;
        @SerializedName("type")
        public String type;
    }
}
