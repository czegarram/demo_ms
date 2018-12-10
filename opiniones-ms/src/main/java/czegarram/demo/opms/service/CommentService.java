package czegarram.demo.opms.service;

import czegarram.demo.opms.persistence.dto.Comment;
import czegarram.demo.opms.util.CommentException;

import java.util.Date;
import java.util.List;

public interface CommentService {

    Comment createComment(Comment comment) throws CommentException;
    boolean updateCommentById(Comment comment);
    boolean deleteCommentById(Long commentId);
    Comment getCommentById(Long commentId);
    Comment getCommentByPurchase(String purchaseId);
    List<Comment> getCommentsByUser(String userId, Date from, Date to);
    List<Comment> getCommentsByStore(String storeId, Date from, Date to);
}
