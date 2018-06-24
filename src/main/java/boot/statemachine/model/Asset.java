package boot.statemachine.model;

import boot.statemachine.config.ImageProcessingStateMachine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Asset {

    @Id
    @GeneratedValue
    private Long id;

    private Long workflowId;

    private Date createDate;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ImageProcessingStateMachine.State getState() {
        return ImageProcessingStateMachine.State.valueOf(state);
    }

    public void setState(ImageProcessingStateMachine.State state) {
        this.state = state.name();
    }
}
