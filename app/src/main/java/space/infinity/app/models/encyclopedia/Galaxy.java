package space.infinity.app.models.encyclopedia;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by icatalin on 18.02.2018.
 */

public class Galaxy implements Parcelable{

    private int id;
    private String name;
    private String description;
    private String image;
    private String designation;
    private String diameter;
    private String distance;
    private String mass;
    private String constellation;
    private String galaxyGroup;
    private String numberOfStars;
    private String type;
    private String facts;

    public Galaxy(int id, String name, String description, String image, String designation,
                  String diameter, String distance, String mass, String constellation,
                  String galaxyGroup, String numberOfStars, String type, String facts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.designation = designation;
        this.diameter = diameter;
        this.distance = distance;
        this.mass = mass;
        this.constellation = constellation;
        this.galaxyGroup = galaxyGroup;
        this.numberOfStars = numberOfStars;
        this.type = type;
        this.facts = facts;
    }

    public Galaxy(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        image = in.readString();
        designation = in.readString();
        diameter = in.readString();
        distance = in.readString();
        mass = in.readString();
        constellation = in.readString();
        galaxyGroup = in.readString();
        numberOfStars = in.readString();
        type = in.readString();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        parcel.writeString(designation);
        parcel.writeString(diameter);
        parcel.writeString(distance);
        parcel.writeString(mass);
        parcel.writeString(constellation);
        parcel.writeString(galaxyGroup);
        parcel.writeString(numberOfStars);
        parcel.writeString(type);
        parcel.writeString(facts);
    }

    public static final Parcelable.Creator<Galaxy> CREATOR = new Parcelable.Creator<Galaxy>() {
        public Galaxy createFromParcel(Parcel in) {
            return new Galaxy(in);
        }
        public Galaxy[] newArray(int size) {
            return new Galaxy[size];
        }
    };
}
