package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "stars")
public class Star extends CosmicItem implements Parcelable {

    @ColumnInfo(name = "age")
    private String age;

    @ColumnInfo(name = "type")
    private String starType;

    @ColumnInfo(name = "diameter")
    private String diameter;

    @ColumnInfo(name = "mass")
    private String mass;

    @ColumnInfo(name = "surface_temperature")
    private String surfaceTemperature;

    @ColumnInfo(name = "facts")
    private String facts;

    public Star() {
        super();
    }

    protected Star(Parcel in) {
        setType(in.readString());
        setId(in.readLong());
        setName(in.readString());
        setDescription(in.readString());
        setImage(in.readString());
        age = in.readString();
        starType = in.readString();
        diameter = in.readString();
        mass = in.readString();
        surfaceTemperature = in.readString();
        facts = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getType());
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeString(getImage());
        dest.writeString(age);
        dest.writeString(starType);
        dest.writeString(diameter);
        dest.writeString(mass);
        dest.writeString(surfaceTemperature);
        dest.writeString(facts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Star> CREATOR = new Creator<Star>() {
        @Override
        public Star createFromParcel(Parcel in) {
            return new Star(in);
        }

        @Override
        public Star[] newArray(int size) {
            return new Star[size];
        }
    };

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
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

    public String getSurfaceTemperature() {
        return surfaceTemperature;
    }

    public void setSurfaceTemperature(String surfaceTemperature) {
        this.surfaceTemperature = surfaceTemperature;
    }
}
