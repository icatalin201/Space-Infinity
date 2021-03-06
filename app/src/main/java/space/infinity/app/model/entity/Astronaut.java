package space.infinity.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "astronauts")
public class Astronaut implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "personal_data")
    private String personalData;

    @ColumnInfo(name = "summary")
    private String summary;

    @ColumnInfo(name = "education")
    private String education;

    @ColumnInfo(name = "experience")
    private String experience;

    @ColumnInfo(name = "image")
    private String image;

    public Astronaut() { }

    protected Astronaut(Parcel in) {
        id = in.readLong();
        name = in.readString();
        personalData = in.readString();
        summary = in.readString();
        education = in.readString();
        experience = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(personalData);
        dest.writeString(summary);
        dest.writeString(education);
        dest.writeString(experience);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Astronaut> CREATOR = new Creator<Astronaut>() {
        @Override
        public Astronaut createFromParcel(Parcel in) {
            return new Astronaut(in);
        }

        @Override
        public Astronaut[] newArray(int size) {
            return new Astronaut[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName() + " " + this.getId();
    }
}
