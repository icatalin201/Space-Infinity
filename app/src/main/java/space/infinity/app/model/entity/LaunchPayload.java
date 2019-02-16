package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LaunchPayload implements Parcelable {

    private Integer id;
    private String name;
    private String description;
    private String dimensions;
    private String weight;
    private Integer total;

    public LaunchPayload() { }

    private LaunchPayload(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        description = in.readString();
        dimensions = in.readString();
        weight = in.readString();
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readInt();
        }
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
        dest.writeString(description);
        dest.writeString(dimensions);
        dest.writeString(weight);
        if (total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(total);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchPayload> CREATOR = new Creator<LaunchPayload>() {
        @Override
        public LaunchPayload createFromParcel(Parcel in) {
            return new LaunchPayload(in);
        }

        @Override
        public LaunchPayload[] newArray(int size) {
            return new LaunchPayload[size];
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
