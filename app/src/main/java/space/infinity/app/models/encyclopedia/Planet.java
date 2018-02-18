package space.infinity.app.models.encyclopedia;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by icatalin on 18.02.2018.
 */

public class Planet implements Parcelable {

    private int id;
    private String name;
    private String description;
    private String image;
    private String diameter;
    private String mass;
    private String moons;
    private String orbitDistance;
    private String orbitPeriod;
    private String surfaceTemperature;
    private String firstRecord;
    private String recordedBy;
    private String facts;

    public Planet(int id, String name, String description, String image, String diameter,
                  String mass, String moons, String orbitDistance, String orbitPeriod,
                  String surfaceTemperature, String firstRecord, String recordedBy, String facts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.diameter = diameter;
        this.mass = mass;
        this.moons = moons;
        this.orbitDistance = orbitDistance;
        this.orbitPeriod = orbitPeriod;
        this.surfaceTemperature = surfaceTemperature;
        this.firstRecord = firstRecord;
        this.recordedBy = recordedBy;
        this.facts = facts;
    }

    public Planet(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        image = in.readString();
        diameter = in.readString();
        mass = in.readString();
        moons = in.readString();
        orbitDistance = in.readString();
        orbitPeriod = in.readString();
        surfaceTemperature = in.readString();
        firstRecord = in.readString();
        recordedBy = in.readString();
        facts = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getMoons() {
        return moons;
    }

    public void setMoons(String moons) {
        this.moons = moons;
    }

    public String getOrbitDistance() {
        return orbitDistance;
    }

    public void setOrbitDistance(String orbitDistance) {
        this.orbitDistance = orbitDistance;
    }

    public String getOrbitPeriod() {
        return orbitPeriod;
    }

    public void setOrbitPeriod(String orbitPeriod) {
        this.orbitPeriod = orbitPeriod;
    }

    public String getSurfaceTemperature() {
        return surfaceTemperature;
    }

    public void setSurfaceTemperature(String surfaceTemperature) {
        this.surfaceTemperature = surfaceTemperature;
    }

    public String getFirstRecord() {
        return firstRecord;
    }

    public void setFirstRecord(String firstRecord) {
        this.firstRecord = firstRecord;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(image);
        parcel.writeString(diameter);
        parcel.writeString(mass);
        parcel.writeString(moons);
        parcel.writeString(orbitDistance);
        parcel.writeString(orbitPeriod);
        parcel.writeString(surfaceTemperature);
        parcel.writeString(firstRecord);
        parcel.writeString(recordedBy);
        parcel.writeString(facts);
    }

    public static final Parcelable.Creator<Planet> CREATOR = new Parcelable.Creator<Planet>() {
        public Planet createFromParcel(Parcel in) {
            return new Planet(in);
        }
        public Planet[] newArray(int size) {
            return new Planet[size];
        }
    };
}
