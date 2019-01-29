package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity(tableName = "launch_sites")
public class LaunchSite implements Parcelable {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    private List<LaunchSiteLocation> launchSiteLocations;

    @ColumnInfo(name = "image")
    private String image;

    public LaunchSite() { }

    private LaunchSite(Parcel in) {
        id = in.readLong();
        name = in.readString();
        launchSiteLocations = in.createTypedArrayList(LaunchSiteLocation.CREATOR);
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeTypedList(launchSiteLocations);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchSite> CREATOR = new Creator<LaunchSite>() {
        @Override
        public LaunchSite createFromParcel(Parcel in) {
            return new LaunchSite(in);
        }

        @Override
        public LaunchSite[] newArray(int size) {
            return new LaunchSite[size];
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

    public List<LaunchSiteLocation> getLaunchSiteLocations() {
        return launchSiteLocations;
    }

    public void setLaunchSiteLocations(List<LaunchSiteLocation> launchSiteLocations) {
        this.launchSiteLocations = launchSiteLocations;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
