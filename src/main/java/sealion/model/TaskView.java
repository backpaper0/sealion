package sealion.model;

import org.seasar.doma.Entity;

import sealion.entity.Task;

@Entity
public class TaskView extends Task {

    public String milestoneName;
    public String accountName;
}
