package space.infinity.app.models;

import java.util.List;

public class LaunchLocation {

    private List<LaunchPad> pads;

    public LaunchLocation() { }

    public List<LaunchPad> getPads() {
        return pads;
    }

    public void setPads(List<LaunchPad> pads) {
        this.pads = pads;
    }
}
