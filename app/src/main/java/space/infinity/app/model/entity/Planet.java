package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "planets")
public class Planet extends CosmicItem implements Parcelable {

    @ColumnInfo(name = "diameter")
    private String diameter;

    @ColumnInfo(name = "mass")
    private String mass;

    @ColumnInfo(name = "moons")
    private String moons;

    @ColumnInfo(name = "orbit_distance")
    private String orbitDistance;

    @ColumnInfo(name = "orbit_period")
    private String orbitPeriod;

    @ColumnInfo(name = "surface_temperature")
    private String surfaceTemperature;

    @ColumnInfo(name = "first_record")
    private String firstRecord;

    @ColumnInfo(name = "recorded_by")
    private String recordedBy;

    @ColumnInfo(name = "facts")
    private String facts;

    public Planet() {
        super();
    }

    private Planet(Parcel in) {
        setType(in.readString());
        setId(in.readLong());
        setName(in.readString());
        setDescription(in.readString());
        setImage(in.readString());
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getType());
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeString(getImage());
        dest.writeString(diameter);
        dest.writeString(mass);
        dest.writeString(moons);
        dest.writeString(orbitDistance);
        dest.writeString(orbitPeriod);
        dest.writeString(surfaceTemperature);
        dest.writeString(firstRecord);
        dest.writeString(recordedBy);
        dest.writeString(facts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Planet> CREATOR = new Creator<Planet>() {
        @Override
        public Planet createFromParcel(Parcel in) {
            return new Planet(in);
        }

        @Override
        public Planet[] newArray(int size) {
            return new Planet[size];
        }
    };

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

}
