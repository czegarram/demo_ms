package czegarram.demo.opms.integration;

import czegarram.demo.opms.persistence.dto.Comment;

public interface CommentEmitter {

    void emitCreation(Comment comment);
    void emitUpdate(Comment comment);
    void emitDeletion(Comment comment);

}
