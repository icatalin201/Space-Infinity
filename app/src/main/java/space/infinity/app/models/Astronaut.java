package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "astronauts")
public class Astronaut {

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

    @ColumnInfo(name = "nasa_experience")
    private String nasaExperience;

    @ColumnInfo(name = "image")
    private String image;

    public Astronaut() { }

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

    public String getNasaExperience() {
        return nasaExperience;
    }

    public void setNasaExperience(String nasaExperience) {
        this.nasaExperience = nasaExperience;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
