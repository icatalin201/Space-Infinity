package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "galaxies")
public class Galaxy extends CosmicItem implements Parcelable {

    @ColumnInfo(name = "designation")
    private String designation;

    @ColumnInfo(name = "diameter")
    private String diameter;

    @ColumnInfo(name = "distance")
    private String distance;

    @ColumnInfo(name = "mass")
    private String mass;

    @ColumnInfo(name = "constellation")
    private String constellation;

    @ColumnInfo(name = "galaxy_group")
    private String galaxyGroup;

    @ColumnInfo(name = "number_of_stars")
    private String numberOfStars;

    @ColumnInfo(name = "type")
    private String galaxyType;

    @ColumnInfo(name = "facts")
    private String facts;

    public Galaxy() {
        super();
    }

    protected Galaxy(Parcel in) {
        setType(in.readString());
        setId(in.readLong());
        setName(in.readString());
        setDescription(in.readString());
        setImage(in.readString());
        designation = in.readString();
        diameter = in.readString();
        distance = in.readString();
        mass = in.readString();
        constellation = in.readString();
        galaxyGroup = in.readString();
        numberOfStars = in.readString();
        galaxyType = in.readString();
        facts = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getType());
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeString(getImage());
        dest.writeString(designation);
        dest.writeString(diameter);
        dest.writeString(distance);
        dest.writeString(mass);
        dest.writeString(constellation);
        dest.writeString(galaxyGroup);
        dest.writeString(numberOfStars);
        dest.writeString(galaxyType);
        dest.writeString(facts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Galaxy> CREATOR = new Creator<Galaxy>() {
        @Override
        public Galaxy createFromParcel(Parcel in) {
            return new Galaxy(in);
        }

        @Override
        public Galaxy[] newArray(int size) {
            return new Galaxy[size];
        }
    };

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getGalaxyGroup() {
        return galaxyGroup;
    }

    public void setGalaxyGroup(String galaxyGroup) {
        this.galaxyGroup = galaxyGroup;
    }

    public String getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(String numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public String getGalaxyType() {
        return galaxyType;
    }

    public void setGalaxyType(String galaxyType) {
        this.galaxyType = galaxyType;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }
}
