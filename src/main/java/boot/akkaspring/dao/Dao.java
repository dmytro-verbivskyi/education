package boot.akkaspring.dao;

import boot.akkaspring.model.DataItem;

import java.util.List;

public interface Dao {
    List<DataItem> retrieveItems(int maxSize);
}
