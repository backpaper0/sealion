package sealion.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import sealion.domain.Key;
import sealion.domain.TaskStatus;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key<Task> id;
    public String title;
    public String content;
    public TaskStatus status;
    public Key<Account> postedBy;
    public LocalDateTime postedAt;
    public Key<Milestone> milestone;
}
