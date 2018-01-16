package space.infinity.app.models;

/**
 * Created by Catalin on 12/28/2017.
 */

public class Coordinates {

    private String lat;
    private String lon;

    public Coordinates(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
