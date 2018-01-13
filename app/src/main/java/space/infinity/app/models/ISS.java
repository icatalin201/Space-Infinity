package space.infinity.app.models;

import java.util.List;

/**
 * Created by Catalin on 1/13/2018.
 */

public class ISS {

    private String message;
    private List<Coordinates> iss_position;

    public ISS(String message, List<Coordinates> iss_position) {
        this.message = message;
        this.iss_position = iss_position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Coordinates> getIss_position() {
        return iss_position;
    }

    public void setIss_position(List<Coordinates> iss_position) {
        this.iss_position = iss_position;
    }
}
