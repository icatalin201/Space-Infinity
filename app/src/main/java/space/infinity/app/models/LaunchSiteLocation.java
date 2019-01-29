package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "launch_site_locations")
public class LaunchSiteLocation implements Parcelable {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "pad_type")
    private int padType;

    @ColumnInfo(name = "launch_site_id")
    private long launchSiteId;

    public LaunchSiteLocation() { }

    private LaunchSiteLocation(Parcel in) {
        id = in.readLong();
        name = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        padType = in.readInt();
        launchSiteId = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeInt(padType);
        dest.writeLong(launchSiteId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchSiteLocation> CREATOR = new Creator<LaunchSiteLocation>() {
        @Override
        public LaunchSiteLocation createFromParcel(Parcel in) {
            return new LaunchSiteLocation(in);
        }

        @Override
        public LaunchSiteLocation[] newArray(int size) {
            return new LaunchSiteLocation[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getPadType() {
        return padType;
    }

    public void setPadType(int padType) {
        this.padType = padType;
    }

    public long getLaunchSiteId() {
        return launchSiteId;
    }

    public void setLaunchSiteId(long launchSiteId) {
        this.launchSiteId = launchSiteId;
    }
}
