package space.infinity.app.models.gallery;

import java.util.List;

/**
 * Created by icatalin on 10.02.2018.
 */

public class ImageCollection {

    private List<Links> links;
    private List<ImageData> items;

    public ImageCollection(List<Links> links, List<ImageData> items) {
        this.links = links;
        this.items = items;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }

    public List<ImageData> getItems() {
        return items;
    }

    public void setItems(List<ImageData> items) {
        this.items = items;
    }
}
