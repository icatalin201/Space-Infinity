package space.infinity.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Launch implements Parcelable {

    private String name;
    private String windowstart;
    private String windowend;
    private String net;
    private Long wsstamp;
    private Long westamp;
    private Long netstamp;
    private String[] infoURLs;
    private String[] vidURLs;
    private Integer status;
    private String holdreason;
    private String failreason;
    private String hashtag;
    private LaunchLocation location;
    private LaunchRocket rocket;
    private List<LaunchMission> missions;

    public Launch() { }

    protected Launch(Parcel in) {
        name = in.readString();
        windowstart = in.readString();
        windowend = in.readString();
        net = in.readString();
        if (in.readByte() == 0) {
            wsstamp = null;
        } else {
            wsstamp = in.readLong();
        }
        if (in.readByte() == 0) {
            westamp = null;
        } else {
            westamp = in.readLong();
        }
        if (in.readByte() == 0) {
            netstamp = null;
        } else {
            netstamp = in.readLong();
        }
        infoURLs = in.createStringArray();
        vidURLs = in.createStringArray();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        holdreason = in.readString();
        failreason = in.readString();
        hashtag = in.readString();
        location = in.readParcelable(LaunchLocation.class.getClassLoader());
        rocket = in.readParcelable(LaunchRocket.class.getClassLoader());
        missions = in.createTypedArrayList(LaunchMission.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(windowstart);
        dest.writeString(windowend);
        dest.writeString(net);
        if (wsstamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(wsstamp);
        }
        if (westamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(westamp);
        }
        if (netstamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(netstamp);
        }
        dest.writeStringArray(infoURLs);
        dest.writeStringArray(vidURLs);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeString(holdreason);
        dest.writeString(failreason);
        dest.writeString(hashtag);
        dest.writeParcelable(location, flags);
        dest.writeParcelable(rocket, flags);
        dest.writeTypedList(missions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Launch> CREATOR = new Creator<Launch>() {
        @Override
        public Launch createFromParcel(Parcel in) {
            return new Launch(in);
        }

        @Override
        public Launch[] newArray(int size) {
            return new Launch[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWindowstart() {
        return windowstart;
    }

    public void setWindowstart(String windowstart) {
        this.windowstart = windowstart;
    }

    public String getWindowend() {
        return windowend;
    }

    public void setWindowend(String windowend) {
        this.windowend = windowend;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public Long getWsstamp() {
        return wsstamp;
    }

    public void setWsstamp(Long wsstamp) {
        this.wsstamp = wsstamp;
    }

    public Long getWestamp() {
        return westamp;
    }

    public void setWestamp(Long westamp) {
        this.westamp = westamp;
    }

    public Long getNetstamp() {
        return netstamp;
    }

    public void setNetstamp(Long netstamp) {
        this.netstamp = netstamp;
    }

    public String[] getInfoURLs() {
        return infoURLs;
    }

    public void setInfoURLs(String[] infoURLs) {
        this.infoURLs = infoURLs;
    }

    public String[] getVidURLs() {
        return vidURLs;
    }

    public void setVidURLs(String[] vidURLs) {
        this.vidURLs = vidURLs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHoldreason() {
        return holdreason;
    }

    public void setHoldreason(String holdreason) {
        this.holdreason = holdreason;
    }

    public String getFailreason() {
        return failreason;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public LaunchLocation getLocation() {
        return location;
    }

    public void setLocation(LaunchLocation location) {
        this.location = location;
    }

    public LaunchRocket getRocket() {
        return rocket;
    }

    public void setRocket(LaunchRocket rocket) {
        this.rocket = rocket;
    }

    public List<LaunchMission> getMissions() {
        return missions;
    }

    public void setMissions(List<LaunchMission> missions) {
        this.missions = missions;
    }
}
