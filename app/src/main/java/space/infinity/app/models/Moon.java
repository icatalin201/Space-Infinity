package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "moons")
public class Moon extends CosmicItem implements Parcelable {

    @ColumnInfo(name = "diameter")
    private String diameter;

    @ColumnInfo(name = "mass")
    private String mass;

    @ColumnInfo(name = "orbits")
    private String orbits;

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

    public Moon() {
        super();
    }

    protected Moon(Parcel in) {
        setType(in.readString());
        setId(in.readLong());
        setName(in.readString());
        setDescription(in.readString());
        setImage(in.readString());
        diameter = in.readString();
        mass = in.readString();
        orbits = in.readString();
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
        dest.writeString(orbits);
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

    public static final Creator<Moon> CREATOR = new Creator<Moon>() {
        @Override
        public Moon createFromParcel(Parcel in) {
            return new Moon(in);
        }

        @Override
        public Moon[] newArray(int size) {
            return new Moon[size];
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

    public String getOrbits() {
        return orbits;
    }

    public void setOrbits(String orbits) {
        this.orbits = orbits;
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
