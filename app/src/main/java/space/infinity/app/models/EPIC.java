package space.infinity.app.models;

import java.util.List;

/**
 * Created by Catalin on 12/28/2017.
 */

public class EPIC {

    private String identifier;
    private String caption;
    private String image;
    private String version;
    private List<Coordinates> centroid_coordinates;
    private String date;

    public EPIC(String identifier, String caption, String image, String version, List<Coordinates> centroid_coordinates, String date) {
        this.identifier = identifier;
        this.caption = caption;
        this.image = image;
        this.version = version;
        this.centroid_coordinates = centroid_coordinates;
        this.date = date;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Coordinates> getCentroid_coordinates() {
        return centroid_coordinates;
    }

    public void setCentroid_coordinates(List<Coordinates> centroid_coordinates) {
        this.centroid_coordinates = centroid_coordinates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
