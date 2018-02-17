package space.infinity.app.models.facts;

/**
 * Created by icatalin on 16.02.2018.
 */

public class SpaceFact {

    private int id;
    private String name;
    private String isFav;

    public SpaceFact(int id, String name, String isFav) {
        this.id = id;
        this.name = name;
        this.isFav = isFav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }
}
