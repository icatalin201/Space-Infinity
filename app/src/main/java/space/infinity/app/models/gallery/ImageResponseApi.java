package space.infinity.app.models.gallery;

/**
 * Created by icatalin on 10.02.2018.
 */

public class ImageResponseApi {

    private ImageCollection collection;

    public ImageResponseApi(ImageCollection collection) {
        this.collection = collection;
    }

    public ImageCollection getCollection() {
        return collection;
    }

    public void setCollection(ImageCollection collection) {
        this.collection = collection;
    }
}
