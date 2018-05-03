package boot.akkaspring.model;

import java.time.LocalDateTime;

public class DataItem {
    private LocalDateTime time;
    private Double value;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
