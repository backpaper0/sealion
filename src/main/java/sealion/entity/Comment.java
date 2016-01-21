package sealion.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.domain.PostedDate;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key<Comment> id;
    public Key<Task> task;
    public MarkedText content;
    public Key<Account> postedBy;
    public PostedDate postedAt;
}
