package org.blog.blogging.services;

import lombok.RequiredArgsConstructor;
import org.blog.blogging.entities.Comment;
import org.blog.blogging.entities.Customer;
import org.blog.blogging.entities.Post;
import org.blog.blogging.exceptions.ResourceNotFoundException;
import org.blog.blogging.payloads.CommentDTO;
import org.blog.blogging.repositories.CommentRepository;
import org.blog.blogging.repositories.CustomerRepository;
import org.blog.blogging.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;


    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId,Integer customerId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(String.format("Customer with Id %s Not found", customerId)));
        Customer customer=customerRepository.findById(customerId).orElseThrow(()-> {
            return new ResourceNotFoundException(String.format("Customer with Id %s Not found", customerId));
        });
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        comment.setCustomer(customer);
        commentDTO.setCustomerId(customerId);
        Comment savedComment = commentRepository.save(comment);

        CommentDTO savedCommentDTO = modelMapper.map(savedComment, CommentDTO.class);

        return savedCommentDTO;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            return new ResourceNotFoundException(String.format("Comment with Id %s Not found", commentId));
        });
        commentRepository.delete(comment);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Integer commentId, Integer customerId) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!existingComment.getPost().getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("You are not authorized to update this comment");
        }

        existingComment.setContent(commentDTO.getContent());

        Comment updatedComment = commentRepository.save(existingComment);

        CommentDTO updatedCommentDTO = modelMapper.map(updatedComment, CommentDTO.class);

        return updatedCommentDTO;
    }

    @Override
    public CommentDTO getCommentById(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        return commentDTO;
    }
}
