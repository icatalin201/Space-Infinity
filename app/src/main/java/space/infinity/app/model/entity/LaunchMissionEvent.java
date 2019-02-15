package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LaunchMissionEvent implements Parcelable {

    private Integer id;
    private String name;
    private Integer relativeTime;
    private Integer duration;
    private String description;

    public LaunchMissionEvent() { }

    private LaunchMissionEvent(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            relativeTime = null;
        } else {
            relativeTime = in.readInt();
        }
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        description = in.readString();
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
        if (relativeTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(relativeTime);
        }
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchMissionEvent> CREATOR = new Creator<LaunchMissionEvent>() {
        @Override
        public LaunchMissionEvent createFromParcel(Parcel in) {
            return new LaunchMissionEvent(in);
        }

        @Override
        public LaunchMissionEvent[] newArray(int size) {
            return new LaunchMissionEvent[size];
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

    public Integer getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(Integer relativeTime) {
        this.relativeTime = relativeTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
