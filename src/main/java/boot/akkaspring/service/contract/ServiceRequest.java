package boot.akkaspring.service.contract;

import boot.akkaspring.model.DataItem;

public class ServiceRequest {
    private DataItem dataItem;

    public DataItem getDataItem() {
        return dataItem;
    }

    public void setDataItem(DataItem dataItem) {
        this.dataItem = dataItem;
    }
}