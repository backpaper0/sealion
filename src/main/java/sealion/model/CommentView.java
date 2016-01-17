package sealion.model;

import org.seasar.doma.Entity;

import sealion.entity.Comment;

@Entity
public class CommentView extends Comment {

    public String accountName;
}
