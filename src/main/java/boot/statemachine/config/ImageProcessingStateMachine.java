package boot.statemachine.config;

public class ImageProcessingStateMachine {

    public enum State {
        SUBMITTED,
        PAID,
        FULFILLED,
        CANCELLED
    }

    public enum Event {
        PAY,
        FULFILL,
        CANCEL
    }

}
