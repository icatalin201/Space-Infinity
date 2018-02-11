package space.infinity.app.models.launch;

import java.util.List;

/**
 * Created by icatalin on 24.01.2018.
 */

public class SecondStage {

    private List<Payloads> payloads;

    public SecondStage(List<Payloads> payloads) {
        this.payloads = payloads;
    }

    public List<Payloads> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<Payloads> payloads) {
        this.payloads = payloads;
    }
}
