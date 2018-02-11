package space.infinity.app.models.gallery;

/**
 * Created by icatalin on 10.02.2018.
 */

public class Links {

    private String rel;
    private String href;

    public Links(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
