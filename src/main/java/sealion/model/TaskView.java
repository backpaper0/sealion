package sealion.model;

import org.seasar.doma.Entity;

import sealion.domain.Key;
import sealion.domain.MilestoneName;
import sealion.domain.Username;
import sealion.entity.Account;
import sealion.entity.Milestone;
import sealion.entity.Task;

@Entity
public class TaskView extends Task {

    public Key<Milestone> milestone;
    public MilestoneName milestoneName;
    public Username accountName;
    public Key<Account> asignee;
    public Username asigneeName;
}
