package space.infinity.app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "rocket_stages")
public class RocketStage implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "stage_length")
    private double length;

    @ColumnInfo(name = "diameter")
    private double diameter;

    @ColumnInfo(name = "propellan_mass")
    private double propellanMass;

    @ColumnInfo(name = "thrust")
    private double thrust;

    @ColumnInfo(name = "specific_impulse")
    private String specificImpulse;

    @ColumnInfo(name = "burn_time")
    private int burnTime;

    @ColumnInfo(name = "fuel")
    private String fuel;

    @ColumnInfo(name = "rocket_id")
    private long rocketId;

    public RocketStage() { }

    private RocketStage(Parcel in) {
        id = in.readLong();
        length = in.readDouble();
        diameter = in.readDouble();
        propellanMass = in.readDouble();
        thrust = in.readDouble();
        specificImpulse = in.readString();
        burnTime = in.readInt();
        fuel = in.readString();
        rocketId = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeDouble(length);
        dest.writeDouble(diameter);
        dest.writeDouble(propellanMass);
        dest.writeDouble(thrust);
        dest.writeString(specificImpulse);
        dest.writeInt(burnTime);
        dest.writeString(fuel);
        dest.writeLong(rocketId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RocketStage> CREATOR = new Creator<RocketStage>() {
        @Override
        public RocketStage createFromParcel(Parcel in) {
            return new RocketStage(in);
        }

        @Override
        public RocketStage[] newArray(int size) {
            return new RocketStage[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getPropellanMass() {
        return propellanMass;
    }

    public void setPropellanMass(double propellanMass) {
        this.propellanMass = propellanMass;
    }

    public double getThrust() {
        return thrust;
    }

    public void setThrust(double thrust) {
        this.thrust = thrust;
    }

    public String getSpecificImpulse() {
        return specificImpulse;
    }

    public void setSpecificImpulse(String specificImpulse) {
        this.specificImpulse = specificImpulse;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public long getRocketId() {
        return rocketId;
    }

    public void setRocketId(long rocketId) {
        this.rocketId = rocketId;
    }
}
