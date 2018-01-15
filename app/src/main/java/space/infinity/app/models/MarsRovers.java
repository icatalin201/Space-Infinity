package space.infinity.app.models;

import java.util.List;

/**
 * Created by Catalin on 1/14/2018.
 */

public class MarsRovers {

    private List<RoverImages> photos;

    public MarsRovers(List<RoverImages> photos) {
        this.photos = photos;
    }

    public List<RoverImages> getPhotos() {
        return photos;
    }

    public void setPhotos(List<RoverImages> photos) {
        this.photos = photos;
    }
}
