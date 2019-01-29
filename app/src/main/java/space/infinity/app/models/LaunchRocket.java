package space.infinity.app.models;

public class LaunchRocket {

    private Integer id;
    private String name;
    private String wikiURL;
    private String[] infoURLs;
    private String imageURL;
    private Integer[] imageSizes;

    public LaunchRocket() { }

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer[] getImageSizes() {
        return imageSizes;
    }

    public void setImageSizes(Integer[] imageSizes) {
        this.imageSizes = imageSizes;
    }
}
