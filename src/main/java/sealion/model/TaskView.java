package sealion.model;

import org.seasar.doma.Entity;

import sealion.domain.Key;
import sealion.domain.TaskStatus;
import sealion.entity.Milestone;
import sealion.entity.Task;

@Entity
public class TaskView {

    public Key<Task> id;
    public String title;
    public TaskStatus status;
    public Key<Milestone> milestone;
    public String milestoneName;
}
