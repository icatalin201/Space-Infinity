package space.infinity.app.models.gallery;

/**
 * Created by icatalin on 10.02.2018.
 */

public class ImageInfo {

    private String date_created;
    private String location;
    private String media_type;
    private String description;
    private String title;
    private String photographer;
    private String nasa_id;
    private String center;

    public ImageInfo(String date_created, String location, String media_type, String description,
                     String title, String photographer, String nasa_id, String center) {
        this.date_created = date_created;
        this.location = location;
        this.media_type = media_type;
        this.description = description;
        this.title = title;
        this.photographer = photographer;
        this.nasa_id = nasa_id;
        this.center = center;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getNasa_id() {
        return nasa_id;
    }

    public void setNasa_id(String nasa_id) {
        this.nasa_id = nasa_id;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
