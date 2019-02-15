package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LaunchRocket implements Parcelable {

    private Integer id;
    private String name;
    private String wikiURL;
    private String[] infoURLs;
    private String imageURL;
    private Integer[] imageSizes;

    public LaunchRocket() { }

    private LaunchRocket(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        wikiURL = in.readString();
        infoURLs = in.createStringArray();
        imageURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(wikiURL);
        dest.writeStringArray(infoURLs);
        dest.writeString(imageURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchRocket> CREATOR = new Creator<LaunchRocket>() {
        @Override
        public LaunchRocket createFromParcel(Parcel in) {
            return new LaunchRocket(in);
        }

        @Override
        public LaunchRocket[] newArray(int size) {
            return new LaunchRocket[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiURL() {
        return wikiURL;
    }

    public void setWikiURL(String wikiURL) {
        this.wikiURL = wikiURL;
    }

    public String[] getInfoURLs() {
        return infoURLs;
    }

    public void setInfoURLs(String[] infoURLs) {
        this.infoURLs = infoURLs;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer[] getImageSizes() {
        return imageSizes;
    }

    public void setImageSizes(Integer[] imageSizes) {
        this.imageSizes = imageSizes;
    }
}
