package space.infinity.app.models;

/**
 * Created by icatalin on 24.01.2018.
 */

public class Payloads {

    private String payload_id;
    private Boolean reused;
    private String customers;
    private String payload_type;
    private String payload_mass;

    public Payloads(String payload_id, Boolean reused, String customers, String payload_type, String payload_mass) {
        this.payload_id = payload_id;
        this.reused = reused;
        this.customers = customers;
        this.payload_type = payload_type;
        this.payload_mass = payload_mass;
    }

    public String getPayload_id() {
        return payload_id;
    }

    public void setPayload_id(String payload_id) {
        this.payload_id = payload_id;
    }

    public Boolean getReused() {
        return reused;
    }

    public void setReused(Boolean reused) {
        this.reused = reused;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getPayload_type() {
        return payload_type;
    }

    public void setPayload_type(String payload_type) {
        this.payload_type = payload_type;
    }

    public String getPayload_mass() {
        return payload_mass;
    }

    public void setPayload_mass(String payload_mass) {
        this.payload_mass = payload_mass;
    }
}
