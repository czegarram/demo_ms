package czegarram.demo.opms.controller.rest;


import czegarram.demo.opms.persistence.dto.Comment;
import czegarram.demo.opms.service.CommentService;
import czegarram.demo.opms.util.CommentException;
import czegarram.demo.opms.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static czegarram.demo.opms.util.Constants.COMMENT_MAIN_URL;

@RestController
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentRestController.class);

    @PostMapping(value = COMMENT_MAIN_URL + "create")
    public Comment createComment(@RequestBody @Valid Comment comment) throws CommentException{
	    return commentService.createComment(comment);
    }

    @PutMapping(value = COMMENT_MAIN_URL + "{commentId}")
    public void updateComment(@RequestBody Comment comment, @PathVariable Long commentId) throws CommentException{
        comment.setCommentId(commentId);
        boolean success = commentService.updateCommentById(comment);
        if(!success){
            throw new CommentException(Constants.NO_SUCCESS_MESSAGE+"update");
        }
    }

    @DeleteMapping(value = COMMENT_MAIN_URL + "{commentId}")
    public void deleteComment(@PathVariable Long commentId) throws CommentException{
        boolean success = commentService.deleteCommentById(commentId);
        if(!success){
            throw new CommentException(Constants.NO_SUCCESS_MESSAGE+"delete");
        }
    }

    @GetMapping(value = COMMENT_MAIN_URL + "{commentId}")
    public Comment getComment(@PathVariable Long commentId){
        return commentService.getCommentById(commentId);
    }

    @GetMapping(value = COMMENT_MAIN_URL + "byPurchase/{purchaseId}")
    public Comment getCommentByPurchase(@PathVariable String purchaseId){
        return commentService.getCommentByPurchase(purchaseId);
    }

    @GetMapping(value = COMMENT_MAIN_URL + "list/byUser/{userId}")
    public List<Comment> getCommentsByUser(@PathVariable String userId,
                                           @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = Constants.ISO8601_PATTERN)  LocalDateTime fromDate,
                                           @RequestParam(value = "to", required = false)  @DateTimeFormat(pattern = Constants.ISO8601_PATTERN) LocalDateTime toDate,
                                           @RequestParam(value = "maxResults", required = false) Integer maxResults){

        Date from = null, to = null;

        if(fromDate != null){
            from = Date.from(fromDate.toInstant(ZoneOffset.UTC));
        }

        if(toDate != null){
            to = Date.from(toDate.toInstant(ZoneOffset.UTC));
        }


        return commentService.getCommentsByUser(userId, from, to);
    }

    @GetMapping(value = COMMENT_MAIN_URL + "list/byStore/{storeId}")
    public List<Comment> getCommentsByStore(@PathVariable String storeId,
                                            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = Constants.ISO8601_PATTERN)  LocalDateTime fromDate,
                                            @RequestParam(value = "to", required = false)  @DateTimeFormat(pattern = Constants.ISO8601_PATTERN) LocalDateTime toDate,
                                            @RequestParam(value = "maxResults", required = false) Integer maxResults){

        Date from = null, to = null;

        if(fromDate != null){
            from = Date.from(fromDate.toInstant(ZoneOffset.UTC));
        }

        if(toDate != null){
            to = Date.from(toDate.toInstant(ZoneOffset.UTC));
        }

        return commentService.getCommentsByStore(storeId, from, to);
    }


}