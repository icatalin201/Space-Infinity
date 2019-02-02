package space.infinity.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LaunchLocation implements Parcelable {

    private List<LaunchPad> pads;

    public LaunchLocation() { }

    private LaunchLocation(Parcel in) {
        pads = in.createTypedArrayList(LaunchPad.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(pads);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchLocation> CREATOR = new Creator<LaunchLocation>() {
        @Override
        public LaunchLocation createFromParcel(Parcel in) {
            return new LaunchLocation(in);
        }

        @Override
        public LaunchLocation[] newArray(int size) {
            return new LaunchLocation[size];
        }
    };

    public List<LaunchPad> getPads() {
        return pads;
    }

    public void setPads(List<LaunchPad> pads) {
        this.pads = pads;
    }
}
