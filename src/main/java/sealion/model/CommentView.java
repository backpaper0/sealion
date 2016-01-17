package sealion.model;

import java.time.LocalDateTime;

import org.seasar.doma.Entity;

import sealion.domain.Key;
import sealion.entity.Account;

@Entity
public class CommentView {

    public String content;
    public Key<Account> postedBy;
    public LocalDateTime postedAt;
    public String accountName;
}
