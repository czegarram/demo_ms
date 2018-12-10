package czegarram.demo.opms.integration;

import czegarram.demo.opms.persistence.dto.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DummyCommentEmitter implements CommentEmitter{

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyCommentEmitter.class);

    @Override
    public void emitCreation(Comment comment) {
        if(null != comment){
            LOGGER.info("Emitting new comment: "+comment.getCommentId());
        }
    }

    @Override
    public void emitUpdate(Comment comment) {
        if(null != comment){
            LOGGER.info("Emitting comment update: "+comment.getCommentId());
        }
    }

    @Override
    public void emitDeletion(Comment comment) {
        if(null != comment) {
            LOGGER.info("Emitting comment deletion: " + comment.getCommentId());
        }
    }
}
