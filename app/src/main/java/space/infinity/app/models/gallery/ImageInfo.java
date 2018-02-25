package space.infinity.app.models.gallery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by icatalin on 10.02.2018.
 */

public class ImageInfo implements Parcelable{

    private String date_created;
    private String media_type;
    private String description;
    private String title;
    private String photographer;
    private String image;

    public ImageInfo(String date_created, String media_type, String description, String title,
                     String photographer, String image) {
        this.date_created = date_created;
        this.media_type = media_type;
        this.description = description;
        this.title = title;
        this.photographer = photographer;
        this.image = image;
    }

    public ImageInfo(Parcel in) {
        date_created = in.readString();
        media_type = in.readString();
        description = in.readString();
        title = in.readString();
        photographer = in.readString();
        image = in.readString();
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date_created);
        parcel.writeString(media_type);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeString(photographer);
        parcel.writeString(image);
    }

    public static final Parcelable.Creator<ImageInfo> CREATOR = new Parcelable.Creator<ImageInfo>() {
        public ImageInfo createFromParcel(Parcel in) {
            return new ImageInfo(in);
        }
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };
}
