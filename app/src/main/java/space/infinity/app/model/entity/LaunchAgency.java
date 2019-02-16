package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LaunchAgency implements Parcelable {

    private Integer id;
    private String name;
    private String abbrev;
    private String countryCode;
    private String wikiURL;
    private String[] infoURLs;

    public LaunchAgency() { }

    private LaunchAgency(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        abbrev = in.readString();
        countryCode = in.readString();
        wikiURL = in.readString();
        infoURLs = in.createStringArray();
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
        dest.writeString(abbrev);
        dest.writeString(countryCode);
        dest.writeString(wikiURL);
        dest.writeStringArray(infoURLs);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchAgency> CREATOR = new Creator<LaunchAgency>() {
        @Override
        public LaunchAgency createFromParcel(Parcel in) {
            return new LaunchAgency(in);
        }

        @Override
        public LaunchAgency[] newArray(int size) {
            return new LaunchAgency[size];
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

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
}
