package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LaunchSpaceProvider implements Parcelable {

    private String name;
    private String abbrev;
    private String countryCode;
    private String wikiURL;
    private int type;

    public LaunchSpaceProvider() { }

    private LaunchSpaceProvider(Parcel in) {
        name = in.readString();
        abbrev = in.readString();
        countryCode = in.readString();
        wikiURL = in.readString();
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(abbrev);
        dest.writeString(countryCode);
        dest.writeString(wikiURL);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchSpaceProvider> CREATOR = new Creator<LaunchSpaceProvider>() {
        @Override
        public LaunchSpaceProvider createFromParcel(Parcel in) {
            return new LaunchSpaceProvider(in);
        }

        @Override
        public LaunchSpaceProvider[] newArray(int size) {
            return new LaunchSpaceProvider[size];
        }
    };

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
