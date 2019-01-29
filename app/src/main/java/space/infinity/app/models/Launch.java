package space.infinity.app.models;

import java.util.List;

public class Launch {

    private String name;
    private Long wsstamp;
    private Long westamp;
    private Long netstamp;
    private String[] infoURLs;
    private String[] vidURLs;
    private Integer status;
    private String holdreason;
    private String failreason;
    private String hashtag;
    private LaunchLocation location;
    private LaunchRocket rocket;
    private List<LaunchMission> missions;

    public Launch() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWsstamp() {
        return wsstamp;
    }

    public void setWsstamp(Long wsstamp) {
        this.wsstamp = wsstamp;
    }

    public Long getWestamp() {
        return westamp;
    }

    public void setWestamp(Long westamp) {
        this.westamp = westamp;
    }

    public Long getNetstamp() {
        return netstamp;
    }

    public void setNetstamp(Long netstamp) {
        this.netstamp = netstamp;
    }

    public String[] getInfoURLs() {
        return infoURLs;
    }

    public void setInfoURLs(String[] infoURLs) {
        this.infoURLs = infoURLs;
    }

    public String[] getVidURLs() {
        return vidURLs;
    }

    public void setVidURLs(String[] vidURLs) {
        this.vidURLs = vidURLs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHoldreason() {
        return holdreason;
    }

    public void setHoldreason(String holdreason) {
        this.holdreason = holdreason;
    }

    public String getFailreason() {
        return failreason;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public LaunchLocation getLocation() {
        return location;
    }

    public void setLocation(LaunchLocation location) {
        this.location = location;
    }

    public LaunchRocket getRocket() {
        return rocket;
    }

    public void setRocket(LaunchRocket rocket) {
        this.rocket = rocket;
    }

    public List<LaunchMission> getMissions() {
        return missions;
    }

    public void setMissions(List<LaunchMission> missions) {
        this.missions = missions;
    }
}
