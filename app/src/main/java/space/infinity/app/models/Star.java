package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stars")
public class Star extends CosmicItem {

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

    public Star() { }

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
