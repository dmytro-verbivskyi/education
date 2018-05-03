package boot.akkaspring.service;

import boot.akkaspring.service.contract.ServiceRequest;
import boot.akkaspring.service.contract.ServiceResponse;

public interface ExternalService {
    ServiceResponse timeConsumingOperation(ServiceRequest request);
}
