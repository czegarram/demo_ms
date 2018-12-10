package czegarram.demo.opms.repository;

import czegarram.demo.opms.persistence.dto.Comment;
import czegarram.demo.opms.util.CommentException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DummyCommentRepository implements CommentRepository {

    protected static List<Comment> COMMENT_LIST = new ArrayList<>();
    protected static long COUNTER = 1;


    @Override
    public Comment createComment(Comment comment) throws CommentException{

        if(comment == null || comment.getPurchaseId() == null || comment.getPurchaseId().trim().isEmpty()){
            throw new CommentException("Invalid Purchase ID");
        }


        if( this.getCommentByPurchase(comment.getPurchaseId())  != null) {
            throw new CommentException("Comment has already existed for the purchase "+comment.getPurchaseId());
        }else {
            Comment newComment = Comment.builder()
                    .commentId(DummyCommentRepository.COUNTER++)
                    .userId(comment.getUserId())
                    .storeId(comment.getStoreId())
                    .purchaseId(comment.getPurchaseId())
                    .date(comment.getDate())
                    .commentText(comment.getCommentText())
                    .score(comment.getScore())
                    .active(true)
                    .build();

            DummyCommentRepository.COMMENT_LIST.add(newComment);

            return newComment;
        }

    }

    @Override
    public boolean updateCommentById(Comment comment) {

        Optional<Comment> foundComment = DummyCommentRepository.COMMENT_LIST.stream()
                .filter(c->  comment.getCommentId().equals(c.getCommentId())).findFirst();


        foundComment.ifPresent(comment1 -> {
            comment1.setCommentText(comment.getCommentText());
            comment1.setScore(comment.getScore());

        });

        return foundComment.isPresent();
    }

    @Override
    public boolean deleteCommentById(Long commentId) {

        Optional<Comment> foundComment = DummyCommentRepository.COMMENT_LIST.stream()
                .filter(c->  commentId.equals(c.getCommentId())).findFirst();


        foundComment.ifPresent(comment -> comment.setActive(false));


        return foundComment.isPresent();
    }

    @Override
    public Comment getCommentById(Long commentId) {

        if(commentId == null) return null;

        Optional<Comment> foundComment = DummyCommentRepository.COMMENT_LIST.stream()
                .filter(c->  commentId.equals(c.getCommentId()) && c.isActive() ).findFirst();

        return foundComment.orElse(null);

    }

    @Override
    public Comment getCommentByPurchase(String purchaseId) {

        if(purchaseId == null) return null;

        Optional<Comment> foundComment = DummyCommentRepository.COMMENT_LIST.stream()
                .filter(c->  purchaseId.equals(c.getPurchaseId())  && c.isActive()).findFirst();

        return foundComment.orElse(null);
    }

    @Override
    public List<Comment> getCommentsByUser(String userId, Date from, Date to) {
        return DummyCommentRepository.COMMENT_LIST.stream()
                .filter(c->  userId.equals(c.getUserId()) && ( from == null || from.compareTo(c.getDate()) <= 0)
                        && (to == null || to.compareTo(c.getDate()) >= 0)  && c.isActive() ).collect(Collectors.toList());
    }

    @Override
    public List<Comment> getCommentsByStore(String storeId, Date from, Date to) {
        return DummyCommentRepository.COMMENT_LIST.stream()
                .filter(c->  storeId.equals(c.getStoreId()) && ( from == null || from.compareTo(c.getDate()) <= 0)
                        && (to == null || to.compareTo(c.getDate()) >= 0)  && c.isActive() ).collect(Collectors.toList());
    }
}
