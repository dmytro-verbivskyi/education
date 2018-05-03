package boot.akkaspring.model;

public enum StatusCodeType {
    SUCCESS("Success"),
    ERROR("Error");

    public final String value;

    StatusCodeType(String value) {
        this.value = value;
    }

    public static String valueOf(StatusCodeType statusCodeType) {
        return statusCodeType == null ? null : statusCodeType.value;
    }
}