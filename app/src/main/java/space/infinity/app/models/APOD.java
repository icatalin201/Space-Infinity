package space.infinity.app.models;

/**
 * Created by Catalin on 12/28/2017.
 */

public class APOD {

    private String date;
    private String explanation;
    private String url;
    private String hdurl;
    private String media_type;
    private String title;
    private String service_version;

    public APOD(String date, String explanation, String url, String hdurl, String media_type, String title, String service_version) {
        this.date = date;
        this.explanation = explanation;
        this.url = url;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.title = title;
        this.service_version = service_version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getService_version() {
        return service_version;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }
}
