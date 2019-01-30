package space.infinity.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LaunchMission implements Parcelable {

    private Integer id;
    private String name;
    private String description;
    private String wikiURL;
    private String[] infoURLs;
    private List<LaunchAgency> agencies;
    private List<LaunchMissionEvent> events;
    private List<LaunchPayload> payloads;

    public LaunchMission() { }

    private LaunchMission(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        description = in.readString();
        wikiURL = in.readString();
        infoURLs = in.createStringArray();
        agencies = in.createTypedArrayList(LaunchAgency.CREATOR);
        events = in.createTypedArrayList(LaunchMissionEvent.CREATOR);
        payloads = in.createTypedArrayList(LaunchPayload.CREATOR);
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
        dest.writeString(wikiURL);
        dest.writeStringArray(infoURLs);
        dest.writeTypedList(agencies);
        dest.writeTypedList(events);
        dest.writeTypedList(payloads);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchMission> CREATOR = new Creator<LaunchMission>() {
        @Override
        public LaunchMission createFromParcel(Parcel in) {
            return new LaunchMission(in);
        }

        @Override
        public LaunchMission[] newArray(int size) {
            return new LaunchMission[size];
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

    public List<LaunchMissionEvent> getEvents() {
        return events;
    }

    public void setEvents(List<LaunchMissionEvent> events) {
        this.events = events;
    }

    public List<LaunchPayload> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<LaunchPayload> payloads) {
        this.payloads = payloads;
    }
}
