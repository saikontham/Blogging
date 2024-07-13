package org.blog.blogging.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.blog.blogging.payloads.ApiResponse;
import org.blog.blogging.payloads.CommentDTO;
import org.blog.blogging.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/customer/{customerId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Integer postId,
                                                    @PathVariable Integer customerId,
                                                    @Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(commentDTO, postId, customerId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}/customer/{customerId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Integer commentId,
                                                    @PathVariable Integer customerId,
                                                    @Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(commentDTO, commentId, customerId);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("deleted comment successfully",true),HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Integer commentId) {
        CommentDTO comment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
