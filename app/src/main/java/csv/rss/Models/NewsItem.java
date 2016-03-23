package csv.rss.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carl on 2016-03-21.
 */
public class NewsItem implements Parcelable {
    private String title;
    private String content;
    private String imageLink;
    private String webPage;

    public NewsItem(String title, String content,String webPage, String imageLink) {
        this.title = title;
        this.content = content;
        this.imageLink = imageLink;
        this.webPage = webPage;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getWebPage() { return webPage; }


    protected NewsItem(Parcel in) {
        title = in.readString();
        content = in.readString();
        imageLink = in.readString();
        webPage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(imageLink);
        dest.writeString(webPage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
}