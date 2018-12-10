package czegarram.demo.opms.service;

import czegarram.demo.opms.integration.CommentEmitter;
import czegarram.demo.opms.persistence.dto.Comment;
import czegarram.demo.opms.repository.CommentRepository;
import czegarram.demo.opms.util.CommentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentEmitter emitter;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Comment createComment(Comment comment) throws CommentException {
        Comment comm = commentRepository.createComment(comment);
        emitter.emitCreation(comm);
        return comm;
    }

    @Override
    public boolean updateCommentById(Comment comment) {
        boolean success = commentRepository.updateCommentById(comment);

        if(success){
            emitter.emitUpdate(comment);
        }
        return success;

    }

    @Override
    public boolean deleteCommentById(Long commentId) {
        boolean success = commentRepository.deleteCommentById(commentId);

        if(success){
            emitter.emitDeletion(Comment.builder().commentId(commentId).build());
        }
        return success;
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.getCommentById(commentId);
    }

    @Override
    public Comment getCommentByPurchase(String purchaseId) {
        return commentRepository.getCommentByPurchase(purchaseId);
    }

    @Override
    public List<Comment> getCommentsByUser(String userId, Date from, Date to) {
        return commentRepository.getCommentsByUser(userId, from, to);
    }

    @Override
    public List<Comment> getCommentsByStore(String storeId, Date from, Date to) {
        return commentRepository.getCommentsByStore(storeId, from, to);
    }
}
