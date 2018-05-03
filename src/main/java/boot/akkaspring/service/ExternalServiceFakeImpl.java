package boot.akkaspring.service;

import boot.akkaspring.model.StatusCodeType;
import boot.akkaspring.service.contract.ServiceRequest;
import boot.akkaspring.service.contract.ServiceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static boot.akkaspring.model.StatusCodeType.SUCCESS;
import static java.lang.Math.random;
import static java.lang.Thread.sleep;

@Service
public class ExternalServiceFakeImpl implements ExternalService {
    @Value("${delay.base:300}")
    long delayBase;

    @Value("${delay.spread:300}")
    long delaySpread;

    @Override
    public ServiceResponse timeConsumingOperation(ServiceRequest request) {
        try {
            sleep(delayBase + (int) (delaySpread * random()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return buildServiceResponse(SUCCESS);
    }

    private ServiceResponse buildServiceResponse(StatusCodeType statusCode) {
        ServiceResponse result = new ServiceResponse();
        result.setStatusCode(statusCode);
        return result;
    }
}
