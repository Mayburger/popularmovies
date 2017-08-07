package nacoda.android.com.popularmovies.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mayburger on 7/6/2017.
 */

public class ParcelableMovies implements Parcelable {

    // Variables
    private String poster_path;
    private String backdrop_path;
    private String title;
    private String release_date;
    private String id;
    private String overview;
    private float vote_average;
    private float vote_count;

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public void setVote_count(float vote_count) {
        this.vote_count = vote_count;
    }



    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getVote_count() {
        return vote_count;
    }

    public String getId() {
        return id;
    }



    public ParcelableMovies(String poster_path, String backdrop_path, String title, String release_date, String overview, float vote_average, float vote_count, String id) {
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeFloat(this.vote_average);
        dest.writeFloat(this.vote_count);
        dest.writeString(this.id);
    }

    public ParcelableMovies() {
    }

    private ParcelableMovies(Parcel in) {
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.title = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readFloat();
        this.vote_count = in.readFloat();
        this.id = in.readString();
    }

    public static final Creator<ParcelableMovies> CREATOR = new Creator<ParcelableMovies>() {
        @Override
        public ParcelableMovies createFromParcel(Parcel source) {
            return new ParcelableMovies(source);
        }

        @Override
        public ParcelableMovies[] newArray(int size) {
            return new ParcelableMovies[size];
        }
    };
}
