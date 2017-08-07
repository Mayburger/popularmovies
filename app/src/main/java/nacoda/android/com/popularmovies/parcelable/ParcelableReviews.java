package nacoda.android.com.popularmovies.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mayburger on 7/30/2017.
 */

public class ParcelableReviews implements Parcelable {

    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public ParcelableReviews(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    ;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    protected ParcelableReviews(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ParcelableReviews> CREATOR = new Parcelable.Creator<ParcelableReviews>() {
        @Override
        public ParcelableReviews createFromParcel(Parcel source) {
            return new ParcelableReviews(source);
        }

        @Override
        public ParcelableReviews[] newArray(int size) {
            return new ParcelableReviews[size];
        }
    };
}
