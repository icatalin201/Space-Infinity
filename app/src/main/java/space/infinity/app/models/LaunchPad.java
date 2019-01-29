package space.infinity.app.models;

import java.util.List;

public class LaunchPad {

    public interface PadType {
        String LAUNCH = "Launch";
        String LANDING = "Landing";
    }

    private Integer id;
    private String name;
    private Integer padType;
    private String latitude;
    private String longitude;
    private String mapURL;
    private Integer locationid;
    private String wikiURL;
    private String[] infoURLs;
    private List<LaunchAgency> agencies;

    public LaunchPad() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPadType() {
        return padType;
    }

    public void setPadType(Integer padType) {
        this.padType = padType;
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

    public String getMapURL() {
        return mapURL;
    }

    public void setMapURL(String mapURL) {
        this.mapURL = mapURL;
    }

    public Integer getLocationid() {
        return locationid;
    }

    public void setLocationid(Integer locationid) {
        this.locationid = locationid;
    }

    public String getWikiURL() {
        return wikiURL;
    }

    public void setWikiURL(String wikiURL) {
        this.wikiURL = wikiURL;
    }

    public String[] getInfoURLs() {
        return infoURLs;
    }

    public void setInfoURLs(String[] infoURLs) {
        this.infoURLs = infoURLs;
    }

    public List<LaunchAgency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<LaunchAgency> agencies) {
        this.agencies = agencies;
    }
}
