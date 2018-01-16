package space.infinity.app.models;

import java.util.List;

/**
 * Created by Catalin on 1/13/2018.
 */

public class ISS {

    private String name;
    private Integer id;
    private String latitude;
    private String longitude;
    private String altitude;
    private String velocity;
    private String visibiity;
    private String footprint;
    private String timestamp;
    private String units;

    public ISS(String name, Integer id, String latitude, String longitude, String altitude,
               String velocity, String visibiity, String footprint, String timestamp, String units) {
        this.name = name;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.velocity = velocity;
        this.visibiity = visibiity;
        this.footprint = footprint;
        this.timestamp = timestamp;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public String getVisibiity() {
        return visibiity;
    }

    public void setVisibiity(String visibiity) {
        this.visibiity = visibiity;
    }

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
