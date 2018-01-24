package space.infinity.app.models;

/**
 * Created by icatalin on 24.01.2018.
 */

public class Rocket {

    private String rocket_id;
    private String rocket_name;
    private String rocket_type;
    private SecondStage second_stage;

    public Rocket(String rocket_id, String rocket_name, String rocket_type, SecondStage second_stage) {
        this.rocket_id = rocket_id;
        this.rocket_name = rocket_name;
        this.rocket_type = rocket_type;
        this.second_stage = second_stage;
    }

    public String getRocket_id() {
        return rocket_id;
    }

    public void setRocket_id(String rocket_id) {
        this.rocket_id = rocket_id;
    }

    public String getRocket_name() {
        return rocket_name;
    }

    public void setRocket_name(String rocket_name) {
        this.rocket_name = rocket_name;
    }

    public String getRocket_type() {
        return rocket_type;
    }

    public void setRocket_type(String rocket_type) {
        this.rocket_type = rocket_type;
    }

    public SecondStage getSecond_stage() {
        return second_stage;
    }

    public void setSecond_stage(SecondStage second_stage) {
        this.second_stage = second_stage;
    }
}
