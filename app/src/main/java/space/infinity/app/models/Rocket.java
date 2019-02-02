package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity(tableName = "rockets")
public class Rocket implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "cost_per_launch")
    private String costPerLaunch;

    @ColumnInfo(name = "height")
    private double height;

    @ColumnInfo(name = "diameter")
    private double diameter;

    @ColumnInfo(name = "mass")
    private double mass;

    @ColumnInfo(name = "stages")
    private int stages;

    @ColumnInfo(name = "total_launches")
    private int totalLaunches;

    @ColumnInfo(name = "successes")
    private int successes;

    @ColumnInfo(name = "failures")
    private int failures;

    @ColumnInfo(name = "status")
    private String status;

    @Ignore
    private List<RocketStage> rocketStageList;

    public Rocket() { }

    protected Rocket(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image = in.readString();
        description = in.readString();
        costPerLaunch = in.readString();
        height = in.readDouble();
        diameter = in.readDouble();
        mass = in.readDouble();
        stages = in.readInt();
        totalLaunches = in.readInt();
        successes = in.readInt();
        failures = in.readInt();
        status = in.readString();
        rocketStageList = in.createTypedArrayList(RocketStage.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(costPerLaunch);
        dest.writeDouble(height);
        dest.writeDouble(diameter);
        dest.writeDouble(mass);
        dest.writeInt(stages);
        dest.writeInt(totalLaunches);
        dest.writeInt(successes);
        dest.writeInt(failures);
        dest.writeString(status);
        dest.writeTypedList(rocketStageList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rocket> CREATOR = new Creator<Rocket>() {
        @Override
        public Rocket createFromParcel(Parcel in) {
            return new Rocket(in);
        }

        @Override
        public Rocket[] newArray(int size) {
            return new Rocket[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCostPerLaunch() {
        return costPerLaunch;
    }

    public void setCostPerLaunch(String costPerLaunch) {
        this.costPerLaunch = costPerLaunch;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public int getStages() {
        return stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public int getTotalLaunches() {
        return totalLaunches;
    }

    public void setTotalLaunches(int totalLaunches) {
        this.totalLaunches = totalLaunches;
    }

    public int getSuccesses() {
        return successes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }

    public int getFailures() {
        return failures;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RocketStage> getRocketStageList() {
        return rocketStageList;
    }

    public void setRocketStageList(List<RocketStage> rocketStageList) {
        this.rocketStageList = rocketStageList;
    }
}
