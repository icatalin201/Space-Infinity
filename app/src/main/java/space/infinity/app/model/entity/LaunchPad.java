package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LaunchPad implements Parcelable {

    public interface PadType {
        String LAUNCH = "Launch";
        String LANDING = "Landing";
    }

    private Integer id;
    private String name;
    private Integer padType;
    private String latitude;
    private String longitude;
    private String mapURL;
    private Integer locationid;
    private String wikiURL;
    private String[] infoURLs;
    private List<LaunchAgency> agencies;

    public LaunchPad() { }

    private LaunchPad(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            padType = null;
        } else {
            padType = in.readInt();
        }
        latitude = in.readString();
        longitude = in.readString();
        mapURL = in.readString();
        if (in.readByte() == 0) {
            locationid = null;
        } else {
            locationid = in.readInt();
        }
        wikiURL = in.readString();
        infoURLs = in.createStringArray();
        agencies = in.createTypedArrayList(LaunchAgency.CREATOR);
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
        if (padType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(padType);
        }
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(mapURL);
        if (locationid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(locationid);
        }
        dest.writeString(wikiURL);
        dest.writeStringArray(infoURLs);
        dest.writeTypedList(agencies);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchPad> CREATOR = new Creator<LaunchPad>() {
        @Override
        public LaunchPad createFromParcel(Parcel in) {
            return new LaunchPad(in);
        }

        @Override
        public LaunchPad[] newArray(int size) {
            return new LaunchPad[size];
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

    public Integer getPadType() {
        return padType;
    }

    public void setPadType(Integer padType) {
        this.padType = padType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMapURL() {
        return mapURL;
    }

    public void setMapURL(String mapURL) {
        this.mapURL = mapURL;
    }

    public Integer getLocationid() {
        return locationid;
    }

    public void setLocationid(Integer locationid) {
        this.locationid = locationid;
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

    public List<LaunchAgency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<LaunchAgency> agencies) {
        this.agencies = agencies;
    }
}
