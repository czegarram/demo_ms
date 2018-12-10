package czegarram.demo.opms.repository;

import czegarram.demo.opms.persistence.dto.Comment;
import czegarram.demo.opms.util.CommentException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

import java.util.ArrayList;


@RunWith(SpringRunner.class)
public class DummyCommentRepositoryTest {

    private static final String STORE01= "T001", STORE02= "T002", STORE03= "T003";
    private static final String USER01= "czegarram@gmail.com", USER02= "test@gmail.com",
            USER03= "admin@gmail.com";

    private static final String PURCHASE01= "P000001", PURCHASE02= "P000002", PURCHASE03= "P000003", PURCHASE04= "P000004", PURCHASE05= "P000005", PURCHASE06= "P000006";

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public CommentRepository commentRepository() {
            return new DummyCommentRepository();
        }
    }

    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setUp() throws Exception{

        DummyCommentRepository.COMMENT_LIST = new ArrayList<>();
        DummyCommentRepository.COUNTER = 1L;

        Comment comment = Comment.builder().commentText("test").score(1)
                .storeId(STORE03).userId(USER03).purchaseId(PURCHASE01).build();
        commentRepository.createComment(comment);
        comment = Comment.builder().commentText("test2").score(2)
                .storeId(STORE03).userId(USER03).purchaseId(PURCHASE02).build();
        commentRepository.createComment(comment);
        comment = Comment.builder().commentText("test3").score(3)
                .storeId(STORE03).userId(USER03).purchaseId(PURCHASE03).build();
        commentRepository.createComment(comment);
    }

    @Test
    public void addComment() throws Exception {

        Comment comment = Comment.builder().commentText("test").score(3)
                .storeId(STORE01).userId(USER01).purchaseId(PURCHASE04).build();

        int initialSize = DummyCommentRepository.COMMENT_LIST.size();

        Comment result = commentRepository.createComment(comment);
        Assert.assertNotNull( "Comment should have a value", result);
        Assert.assertEquals(initialSize + 1, DummyCommentRepository.COMMENT_LIST.size());
        Assert.assertEquals(new Long(DummyCommentRepository.COUNTER - 1L), result.getCommentId());
    }

    @Test(expected = CommentException.class)
    public void addDuplicatedComment() throws Exception {
        Comment comment = Comment.builder().commentText("test").score(1)
                .storeId(STORE03).userId(USER03).purchaseId(PURCHASE01).build();
        commentRepository.createComment(comment);
    }

    @Test(expected = CommentException.class)
    public void addNullComment()throws Exception {
        Comment result = commentRepository.createComment(null);
        Assert.assertNull(result);
    }

    @Test
    public void updateExistingComment(){

        final String NEW_TEXT = "new test";
        final Integer NEW_SCORE = 3;
        final Long ID = 1L;

        Comment commentForUpdate = Comment.builder().commentText(NEW_TEXT).score(NEW_SCORE)
                .commentId(ID).build();

        boolean success = commentRepository.updateCommentById(commentForUpdate);
        Assert.assertTrue(success);

        Comment comment = commentRepository.getCommentById(ID);
        Assert.assertNotNull(comment);
        Assert.assertEquals(NEW_TEXT, comment.getCommentText());
        Assert.assertEquals(NEW_SCORE, comment.getScore());
    }

    @Test
    public void updateNonExistingComment(){
        final String NEW_TEXT = "new test";
        final Integer NEW_SCORE = 3;
        final Long ID = 99999L;

        Comment commentForUpdate = Comment.builder().commentText(NEW_TEXT).score(NEW_SCORE)
                .commentId(ID).build();

        boolean success = commentRepository.updateCommentById(commentForUpdate);
        Assert.assertFalse(success);

        Comment comment = commentRepository.getCommentById(ID);
        Assert.assertNull(comment);
    }

    @Test
    public void deleteExistingComment(){

        final Long ID = 1L;

        Comment oldComment = commentRepository.getCommentById(ID);



        boolean success = commentRepository.deleteCommentById(ID);
        Assert.assertTrue(success);

        Comment comment = commentRepository.getCommentById(ID);
        Assert.assertNull(comment);
        Assert.assertFalse(oldComment.isActive());
    }

    @Test
    public void deleteNonExistingComment(){

        final Long ID = 99999L;

        boolean success = commentRepository.deleteCommentById(ID);
        Assert.assertFalse(success);
    }

    @Test public void getById(){
        final Long ID = 2L;

        Comment comment = commentRepository.getCommentById(ID);
        Assert.assertNotNull(comment);

    }

    @Test public void getById_NotFound(){
        final Long ID = 9999L;

        Comment comment = commentRepository.getCommentById(ID);
        Assert.assertNull(comment);

    }

    @Test public void getById_Null(){
        Comment comment = commentRepository.getCommentById(null);
        Assert.assertNull(comment);

    }

    @Test public void getByPurchase(){
        Comment comment = commentRepository.getCommentByPurchase(PURCHASE01);
        Assert.assertNotNull(comment);
    }

    @Test public void getByPurchase_NotFound(){
        Comment comment = commentRepository.getCommentByPurchase("TEST");
        Assert.assertNull(comment);
    }

    @Test public void getByPurchase_Null(){
        Comment comment = commentRepository.getCommentByPurchase(null);
        Assert.assertNull(comment);
    }

}
