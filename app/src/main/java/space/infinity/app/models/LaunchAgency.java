package space.infinity.app.models;

public class LaunchAgency {

    private Integer id;
    private String name;
    private String abbrev;
    private String countryCode;
    private String wikiURL;
    private String infoURLs;

    public LaunchAgency() { }

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

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getWikiURL() {
        return wikiURL;
    }

    public void setWikiURL(String wikiURL) {
        this.wikiURL = wikiURL;
    }

    public String getInfoURLs() {
        return infoURLs;
    }

    public void setInfoURLs(String infoURLs) {
        this.infoURLs = infoURLs;
    }
}
