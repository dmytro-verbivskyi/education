package boot.rabbitmq.service.wf1;

public class StringWithClassResult {

    private String message;
    private Class aClass;

    public String getMessage() {
        return message;
    }

    public Class getaClass() {
        return aClass;
    }

    public StringWithClassResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public StringWithClassResult setClass(Class aClass) {
        this.aClass = aClass;
        return this;
    }
}
