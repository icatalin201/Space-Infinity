package space.infinity.app.models.encyclopedia;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by icatalin on 18.02.2018.
 */

public class Other implements Parcelable{

    private int id;
    private String name;
    private String description;
    private String image;
    private String age;
    private String type;
    private String diameter;
    private String mass;
    private String surfaceTemperature;
    private String detailedInfo;
    private String otherInfo;

    public Other(int id, String name, String description, String image, String age,
                 String type, String diameter, String mass, String surfaceTemperature,
                 String detailedInfo, String otherInfo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.age = age;
        this.type = type;
        this.diameter = diameter;
        this.mass = mass;
        this.surfaceTemperature = surfaceTemperature;
        this.detailedInfo = detailedInfo;
        this.otherInfo = otherInfo;
    }

    public Other(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        image = in.readString();
        age = in.readString();
        type = in.readString();
        diameter = in.readString();
        mass = in.readString();
        surfaceTemperature = in.readString();
        detailedInfo = in.readString();
        otherInfo = in.readString();
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDetailedInfo() {
        return detailedInfo;
    }

    public void setDetailedInfo(String detailedInfo) {
        this.detailedInfo = detailedInfo;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
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
        parcel.writeString(age);
        parcel.writeString(type);
        parcel.writeString(diameter);
        parcel.writeString(mass);
        parcel.writeString(surfaceTemperature);
        parcel.writeString(detailedInfo);
        parcel.writeString(otherInfo);
    }

    public static final Parcelable.Creator<Other> CREATOR = new Parcelable.Creator<Other>() {
        public Other createFromParcel(Parcel in) {
            return new Other(in);
        }
        public Other[] newArray(int size) {
            return new Other[size];
        }
    };
}
