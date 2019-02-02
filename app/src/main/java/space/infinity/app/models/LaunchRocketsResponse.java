package space.infinity.app.models;

import java.util.List;

public class LaunchRocketsResponse {

    private List<LaunchRocket> rockets;
    private Integer total;
    private Integer offset;
    private Integer count;

    public LaunchRocketsResponse() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<LaunchRocket> getRockets() {
        return rockets;
    }

    public void setRockets(List<LaunchRocket> rockets) {
        this.rockets = rockets;
    }
}
