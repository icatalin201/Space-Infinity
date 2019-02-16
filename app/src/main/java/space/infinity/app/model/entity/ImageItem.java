package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class ImageItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "date_created")
    private String dateCreated;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "photographer")
    private String photographer;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "hd_image")
    private String hdImage;

    public ImageItem() { }

    private ImageItem(Parcel in) {
        id = in.readLong();
        dateCreated = in.readString();
        description = in.readString();
        title = in.readString();
        photographer = in.readString();
        image = in.readString();
        hdImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(dateCreated);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(photographer);
        dest.writeString(image);
        dest.writeString(hdImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getHdImage() {
        return hdImage;
    }

    public void setHdImage(String hdImage) {
        this.hdImage = hdImage;
    }
}
