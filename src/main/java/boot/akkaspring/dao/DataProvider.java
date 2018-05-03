package boot.akkaspring.dao;

import boot.akkaspring.model.DataItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

@Component
public class DataProvider implements Dao {
    @Override
    public List<DataItem> retrieveItems(int maxSize) {
        List<DataItem> result = new ArrayList<>();
        for (int i = 0; i < maxSize * random(); i++) {
            result.add(buildDataItem());
        }
        return result;
    }

    private DataItem buildDataItem() {
        DataItem dataItem = new DataItem();
        dataItem.setTime(LocalDateTime.now());
        dataItem.setValue(random());
        return dataItem;
    }
}