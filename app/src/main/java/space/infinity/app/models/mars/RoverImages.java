package space.infinity.app.models.mars;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Catalin on 1/14/2018.
 */

public class RoverImages implements Parcelable {

    private Long id;
    private Integer sol;
    private String img_src;
    private String earth_date;
    private Rover rover;

    public RoverImages(Long id, Integer sol, String img_src, String earth_date, Rover rover) {
        this.id = id;
        this.sol = sol;
        this.img_src = img_src;
        this.earth_date = earth_date;
        this.rover = rover;
    }

    public RoverImages(Parcel in) {
        id = in.readLong();
        sol = in.readInt();
        img_src = in.readString();
        earth_date = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSol() {
        return sol;
    }

    public void setSol(Integer sol) {
        this.sol = sol;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getEarth_date() {
        return earth_date;
    }

    public void setEarth_date(String earth_date) {
        this.earth_date = earth_date;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(sol);
        parcel.writeString(img_src);
        parcel.writeString(earth_date);
    }

    public static final Parcelable.Creator<RoverImages> CREATOR = new Parcelable.Creator<RoverImages>() {
        public RoverImages createFromParcel(Parcel in) {
            return new RoverImages(in);
        }
        public RoverImages[] newArray(int size) {
            return new RoverImages[size];
        }
    };
}
