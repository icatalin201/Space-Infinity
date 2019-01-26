package space.infinity.app.models;

import android.arch.persistence.room.Ignore;

public class CosmicItem {

    public interface CosmicType {
        String PLANET = "planet";
        String MOON = "moon";
        String GALAXY = "galaxy";
        String STAR = "star";
    }

    @Ignore
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
