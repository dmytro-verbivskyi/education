package boot.akkaspring.dao;

import boot.akkaspring.model.DataItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataProvider implements Dao {

    @Override
    public List<DataItem> retrieveItems(int maxSize) {
        List<DataItem> result = new ArrayList<>();
        int fractionOfMaxSize = ThreadLocalRandom.current().nextInt(1, maxSize + 1);

        for (int i = 0; i < fractionOfMaxSize; i++) {
            result.add(buildDataItem());
        }
        return result;
    }

    private DataItem buildDataItem() {
        DataItem dataItem = new DataItem();
        dataItem.setTime(LocalDateTime.now());
        dataItem.setValue(ThreadLocalRandom.current().nextDouble());
        return dataItem;
    }
}