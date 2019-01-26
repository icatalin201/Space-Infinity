package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "planets")
public class Planet extends CosmicItem {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image")
    private String image;

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

    public Planet() { }

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

}
