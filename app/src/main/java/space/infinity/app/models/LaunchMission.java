package space.infinity.app.models;

import java.util.List;

public class LaunchMission {

    private Integer id;
    private String name;
    private String description;
    private String wikiURL;
    private String infoURLs;
    private List<LaunchAgency> agencies;
    private List<LaunchMissionEvent> events;
    private List<LaunchPayload> payloads;

    public LaunchMission() { }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<LaunchAgency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<LaunchAgency> agencies) {
        this.agencies = agencies;
    }

    public List<LaunchMissionEvent> getEvents() {
        return events;
    }

    public void setEvents(List<LaunchMissionEvent> events) {
        this.events = events;
    }

    public List<LaunchPayload> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<LaunchPayload> payloads) {
        this.payloads = payloads;
    }
}
