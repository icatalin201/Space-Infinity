package space.infinity.app.models.launch;

/**
 * Created by icatalin on 24.01.2018.
 */

public class Launch {

    private Integer flight_number;
    private String launch_year;
    private Long launch_date_unix;
    private Rocket rocket;
    private LaunchSite launch_site;

    public Launch(Integer flight_number, String launch_year, Long launch_date_unix, Rocket rocket, LaunchSite launch_site) {
        this.flight_number = flight_number;
        this.launch_year = launch_year;
        this.launch_date_unix = launch_date_unix;
        this.rocket = rocket;
        this.launch_site = launch_site;
    }

    public Integer getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(Integer flight_number) {
        this.flight_number = flight_number;
    }

    public String getLaunch_year() {
        return launch_year;
    }

    public void setLaunch_year(String launch_year) {
        this.launch_year = launch_year;
    }

    public Long getLaunch_date_unix() {
        return launch_date_unix;
    }

    public void setLaunch_date_unix(Long launch_date_unix) {
        this.launch_date_unix = launch_date_unix;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public LaunchSite getLaunch_site() {
        return launch_site;
    }

    public void setLaunch_site(LaunchSite launch_site) {
        this.launch_site = launch_site;
    }
}
