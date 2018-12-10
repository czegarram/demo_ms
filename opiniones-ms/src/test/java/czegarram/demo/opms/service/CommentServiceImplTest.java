package czegarram.demo.opms.service;

import czegarram.demo.opms.integration.CommentEmitter;
import czegarram.demo.opms.persistence.dto.Comment;
import czegarram.demo.opms.repository.CommentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CommentServiceImplTest {

    private static final String STORE01= "T001", STORE02= "T002", STORE03= "T003";
    private static final String USER01= "czegarram@gmail.com", USER02= "test@gmail.com",
            USER03= "admin@gmail.com";

    private static final String PURCHASE01= "P000001", PURCHASE02= "P000002", PURCHASE03= "P000003", PURCHASE04= "P000004", PURCHASE05= "P000005", PURCHASE06= "P000006";


    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public CommentService commentRepository() {
            return new CommentServiceImpl();
        }
    }

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentEmitter commentEmitter;

    @MockBean
    private CommentRepository commentRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void createComment() throws Exception{

        Comment comment = Comment.builder().commentId(1L).commentText("test").score(1)
                .storeId(STORE03).userId(USER03).purchaseId(PURCHASE01).build();

        when(commentRepository.createComment(comment)).thenReturn(comment);

        Comment commentResult = commentService.createComment(comment);

        verify( commentEmitter, times(1) ).emitCreation(comment);
        verify( commentRepository, times(1) ).createComment(comment);
        Assert.assertNotNull(commentResult);
    }

    @Test
    public void createComment_null() throws Exception{
        Comment comment = commentService.createComment(null);

        verify( commentEmitter, times(1) ).emitCreation(null);
        verify( commentRepository, times(1) ).createComment(null);
        Assert.assertNull(comment);

    }

    @Test
    public void updateComment() throws Exception{

        Comment comment = Comment.builder().commentId(1L).commentText("test").score(1)
                .storeId(STORE03).userId(USER03).purchaseId(PURCHASE01).build();

        when(commentRepository.updateCommentById(comment)).thenReturn(true);

        boolean success = commentService.updateCommentById(comment);

        verify( commentEmitter, times(1) ).emitUpdate(comment);
        verify( commentRepository, times(1) ).updateCommentById(comment);
        Assert.assertTrue(success);
    }

    @Test
    public void updateComment_null() throws Exception{

        Comment comment = Comment.builder().commentId(1L).commentText("test").score(1)
                .storeId(STORE03).userId(USER03).purchaseId(PURCHASE01).build();

        when(commentRepository.updateCommentById(comment)).thenReturn(false);

        boolean success = commentService.updateCommentById(comment);

        verify( commentEmitter, times(0) ).emitUpdate(comment);
        verify( commentRepository, times(1 )).updateCommentById(comment);
        Assert.assertFalse(success);
    }

    @Test
    public void deleteComment() throws Exception{

        final Long ID = 1L;

        when(commentRepository.deleteCommentById(ID)).thenReturn(true);

        boolean success = commentService.deleteCommentById(ID);

        verify( commentEmitter, times(1) ).emitDeletion(any(Comment.class));
        verify( commentRepository, times(1) ).deleteCommentById(ID);
        Assert.assertTrue(success);
    }

    @Test
    public void deleteComment_Null() throws Exception{

        final Long ID = 1L;

        when(commentRepository.deleteCommentById(ID)).thenReturn(false);

        boolean success = commentService.deleteCommentById(ID);

        verify( commentEmitter, times(0) ).emitDeletion(any(Comment.class));
        verify( commentRepository, times(1) ).deleteCommentById(ID);
        Assert.assertFalse(success);
    }

}
