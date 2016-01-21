package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.domain.PostedDate;
import sealion.domain.TaskStatus;
import sealion.domain.TaskTitle;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key<Task> id;
    public TaskTitle title;
    public MarkedText content;
    public TaskStatus status;
    public Key<Account> postedBy;
    public PostedDate postedAt;
    public Key<Project> project;
    public Key<Milestone> milestone;
}
