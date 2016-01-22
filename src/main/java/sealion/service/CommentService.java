package sealion.service;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import sealion.dao.CommentDao;
import sealion.domain.Key;
import sealion.domain.MarkedText;
import sealion.domain.PostedDate;
import sealion.entity.Comment;
import sealion.entity.Task;
import sealion.session.User;

@ApplicationScoped
@Transactional
public class CommentService {

    @Inject
    private CommentDao commentDao;
    @Inject
    private User user;
    @Inject
    private Clock clock;

    public Comment create(Key<Task> task, MarkedText content) {
        Comment entity = new Comment();
        entity.task = task;
        entity.content = content;
        entity.postedBy = user.getAccount().id;
        entity.postedAt = new PostedDate(LocalDateTime.now(clock));
        commentDao.insert(entity);
        return entity;
    }
}
