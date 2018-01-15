package space.infinity.app.models;

/**
 * Created by Catalin on 1/14/2018.
 */

public class RoverImages {

    private Long id;
    private Integer sol;
    private String img_src;
    private String earth_date;
    private Rover rover;

    public RoverImages(Long id, Integer sol, String img_src, String earth_date, Rover rover) {
        this.id = id;
        this.sol = sol;
        this.img_src = img_src;
        this.earth_date = earth_date;
        this.rover = rover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSol() {
        return sol;
    }

    public void setSol(Integer sol) {
        this.sol = sol;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getEarth_date() {
        return earth_date;
    }

    public void setEarth_date(String earth_date) {
        this.earth_date = earth_date;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }
}
