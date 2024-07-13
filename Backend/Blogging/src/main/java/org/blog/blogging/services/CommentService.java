package org.blog.blogging.services;

import org.blog.blogging.payloads.CommentDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer postId,Integer customerId);

    void deleteComment(Integer commentId);

    CommentDTO updateComment(CommentDTO commentDTO, Integer commentId,Integer customerId);

    CommentDTO getCommentById(Integer commentId);
}
