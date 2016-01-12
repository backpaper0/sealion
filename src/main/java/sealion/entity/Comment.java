package sealion.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import sealion.domain.Key;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key<Comment> id;
    public String content;
    public Key<Account> postedBy;
    public LocalDateTime postedAt;
}
