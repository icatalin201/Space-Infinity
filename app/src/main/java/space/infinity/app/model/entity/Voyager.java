package space.infinity.app.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "voyagers")
public class Voyager {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "launch_date")
    private String launchDate;

    @ColumnInfo(name = "launch_date_stamp")
    private String launchDateStamp;

    @ColumnInfo(name = "distance_from_earth_km")
    private String distanceFromEarthKM;

    @ColumnInfo(name = "distance_from_earth_au")
    private String distanceFromEarthAU;

    @ColumnInfo(name = "distance_from_sun_km")
    private String distanceFromSunKM;

    @ColumnInfo(name = "distance_from_sun_au")
    private String distanceFromSunAU;

    @ColumnInfo(name = "velocity")
    private String velocity;

    @ColumnInfo(name = "one_way_light_time")
    private String oneWayLightTime;

    public Voyager() { }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLaunchDateStamp() {
        return launchDateStamp;
    }

    public void setLaunchDateStamp(String launchDateStamp) {
        this.launchDateStamp = launchDateStamp;
    }

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

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getDistanceFromEarthKM() {
        return distanceFromEarthKM;
    }

    public void setDistanceFromEarthKM(String distanceFromEarthKM) {
        this.distanceFromEarthKM = distanceFromEarthKM;
    }

    public String getDistanceFromEarthAU() {
        return distanceFromEarthAU;
    }

    public void setDistanceFromEarthAU(String distanceFromEarthAU) {
        this.distanceFromEarthAU = distanceFromEarthAU;
    }

    public String getDistanceFromSunKM() {
        return distanceFromSunKM;
    }

    public void setDistanceFromSunKM(String distanceFromSunKM) {
        this.distanceFromSunKM = distanceFromSunKM;
    }

    public String getDistanceFromSunAU() {
        return distanceFromSunAU;
    }

    public void setDistanceFromSunAU(String distanceFromSunAU) {
        this.distanceFromSunAU = distanceFromSunAU;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public String getOneWayLightTime() {
        return oneWayLightTime;
    }

    public void setOneWayLightTime(String oneWayLightTime) {
        this.oneWayLightTime = oneWayLightTime;
    }
}
