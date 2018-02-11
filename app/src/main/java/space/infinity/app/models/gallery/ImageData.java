package space.infinity.app.models.gallery;

import java.util.List;

/**
 * Created by icatalin on 10.02.2018.
 */

public class ImageData {

    private List<ImageInfo> data;
    private List<Links> links;

    public ImageData(List<ImageInfo> data, List<Links> links) {
        this.data = data;
        this.links = links;
    }

    public List<ImageInfo> getData() {
        return data;
    }

    public void setData(List<ImageInfo> data) {
        this.data = data;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }
}
