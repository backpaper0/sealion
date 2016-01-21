package sealion.model;

import org.seasar.doma.Entity;

import sealion.domain.MilestoneName;
import sealion.domain.Username;
import sealion.entity.Task;

@Entity
public class TaskView extends Task {

    public MilestoneName milestoneName;
    public Username accountName;
}
