package boot.akkaspring.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParamsHolder {

    @Value("${actorsCount:4}")
    int actorsCount;

    public int getActorsCount() {
        return actorsCount;
    }

    public void setActorsCount(int actorsCount) {
        this.actorsCount = actorsCount;
    }
}
