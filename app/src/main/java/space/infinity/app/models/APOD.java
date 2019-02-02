package space.infinity.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Catalin on 12/28/2017.
 */

public class APOD implements Parcelable {

    private String date;
    private String explanation;
    private String url;
    private String hdurl;
    private String media_type;
    private String title;
    private String copyright;

    public APOD(){}

    public APOD(String date, String explanation, String url, String hdurl,
                String media_type, String title, String copyright) {
        this.date = date;
        this.explanation = explanation;
        this.url = url;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.title = title;
        this.copyright = copyright;
    }

    private APOD(Parcel in) {
        date = in.readString();
        explanation = in.readString();
        url = in.readString();
        hdurl = in.readString();
        media_type = in.readString();
        title = in.readString();
        copyright = in.readString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String service_version) {
        this.copyright = service_version;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(explanation);
        parcel.writeString(url);
        parcel.writeString(hdurl);
        parcel.writeString(media_type);
        parcel.writeString(title);
        parcel.writeString(copyright);
    }

    public static final Parcelable.Creator<APOD> CREATOR = new Parcelable.Creator<APOD>() {
        public APOD createFromParcel(Parcel in) {
            return new APOD(in);
        }
        public APOD[] newArray(int size) {
            return new APOD[size];
        }
    };
}
