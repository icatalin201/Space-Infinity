package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "galaxies")
public class Galaxy extends CosmicItem {

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

    public Galaxy() { }

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
