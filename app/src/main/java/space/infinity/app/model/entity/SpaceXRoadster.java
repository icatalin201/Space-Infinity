package space.infinity.app.model.entity;

import com.google.gson.annotations.SerializedName;

public class SpaceXRoadster {

    @SerializedName("name")
    private String name;

    @SerializedName("launch_date")
    private String launchDate;

    @SerializedName("launch_date_unix")
    private long launchDateUnix;

    @SerializedName("launch_mass_kg")
    private double launchMassKg;

    @SerializedName("launch_mass_lbs")
    private double launchMassLbs;

    @SerializedName("orbity_type")
    private String orbitType;

    @SerializedName("apoapsis_au")
    private double apoapsisAu;

    @SerializedName("periapsis_au")
    private double periapsisAu;

    @SerializedName("semi_major_axis_au")
    private double semiMajorAxixAu;

    @SerializedName("eccentricity")
    private double eccentricity;

    @SerializedName("inclination")
    private double inclination;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("periapsis_args")
    private double periapsisArg;

    @SerializedName("period_days")
    private double periodDays;

    @SerializedName("speed_kph")
    private double speedKph;

    @SerializedName("speed_mph")
    private double speedMph;

    @SerializedName("earth_distance_km")
    private double earthDistanceKm;

    @SerializedName("earth_distance_mi")
    private double earthDistanceMi;

    @SerializedName("mars_distance_km")
    private double marsDistanceKm;

    @SerializedName("mars_distance_mi")
    private double marsDistanceMi;

    @SerializedName("wikipedia")
    private String wikipedia;

    @SerializedName("details")
    private String details;

    public SpaceXRoadster() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public long getLaunchDateUnix() {
        return launchDateUnix;
    }

    public void setLaunchDateUnix(long launchDateUnix) {
        this.launchDateUnix = launchDateUnix;
    }

    public double getLaunchMassKg() {
        return launchMassKg;
    }

    public void setLaunchMassKg(double launchMassKg) {
        this.launchMassKg = launchMassKg;
    }

    public double getLaunchMassLbs() {
        return launchMassLbs;
    }

    public void setLaunchMassLbs(double launchMassLbs) {
        this.launchMassLbs = launchMassLbs;
    }

    public String getOrbitType() {
        return orbitType;
    }

    public void setOrbitType(String orbitType) {
        this.orbitType = orbitType;
    }

    public double getApoapsisAu() {
        return apoapsisAu;
    }

    public void setApoapsisAu(double apoapsisAu) {
        this.apoapsisAu = apoapsisAu;
    }

    public double getPeriapsisAu() {
        return periapsisAu;
    }

    public void setPeriapsisAu(double periapsisAu) {
        this.periapsisAu = periapsisAu;
    }

    public double getSemiMajorAxixAu() {
        return semiMajorAxixAu;
    }

    public void setSemiMajorAxixAu(double semiMajorAxixAu) {
        this.semiMajorAxixAu = semiMajorAxixAu;
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(double eccentricity) {
        this.eccentricity = eccentricity;
    }

    public double getInclination() {
        return inclination;
    }

    public void setInclination(double inclination) {
        this.inclination = inclination;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getPeriapsisArg() {
        return periapsisArg;
    }

    public void setPeriapsisArg(double periapsisArg) {
        this.periapsisArg = periapsisArg;
    }

    public double getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(double periodDays) {
        this.periodDays = periodDays;
    }

    public double getSpeedKph() {
        return speedKph;
    }

    public void setSpeedKph(double speedKph) {
        this.speedKph = speedKph;
    }

    public double getSpeedMph() {
        return speedMph;
    }

    public void setSpeedMph(double speedMph) {
        this.speedMph = speedMph;
    }

    public double getEarthDistanceKm() {
        return earthDistanceKm;
    }

    public void setEarthDistanceKm(double earthDistanceKm) {
        this.earthDistanceKm = earthDistanceKm;
    }

    public double getEarthDistanceMi() {
        return earthDistanceMi;
    }

    public void setEarthDistanceMi(double earthDistanceMi) {
        this.earthDistanceMi = earthDistanceMi;
    }

    public double getMarsDistanceKm() {
        return marsDistanceKm;
    }

    public void setMarsDistanceKm(double marsDistanceKm) {
        this.marsDistanceKm = marsDistanceKm;
    }

    public double getMarsDistanceMi() {
        return marsDistanceMi;
    }

    public void setMarsDistanceMi(double marsDistanceMi) {
        this.marsDistanceMi = marsDistanceMi;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
