package space.infinity.app.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class CosmicItem {

    public interface CosmicType {
        String PLANET = "planet";
        String MOON = "moon";
        String GALAXY = "galaxy";
        String STAR = "star";
        String OTHER = "other";
    }

    @ColumnInfo(name = "local_type")
    private String type;

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image")
    private String image;

    CosmicItem() { }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
