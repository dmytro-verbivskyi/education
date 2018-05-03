package boot.akkaspring.service.contract;

import boot.akkaspring.model.StatusCodeType;

public class ServiceResponse {
    private String statusCode;
    private String errorCode;
    private String errorDescription;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCodeType statusCode) {
        this.statusCode = StatusCodeType.valueOf(statusCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}