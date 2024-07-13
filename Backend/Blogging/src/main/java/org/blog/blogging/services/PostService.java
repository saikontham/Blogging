package org.blog.blogging.services;

import org.blog.blogging.entities.Post;
import org.blog.blogging.payloads.PostDTO;
import org.blog.blogging.payloads.PostResponse;

import java.util.List;

public interface PostService {


    PostDTO createPost(PostDTO postDTO,Integer customerId,Integer categoryId);
    PostDTO updatePost(PostDTO postDTO,Integer postId);

    void deletePost(Integer postId );
    PostDTO getPost(Integer postId);
    PostResponse getListOfPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortStyle);
    PostResponse getPostsByUser(Integer customerId,Integer pageNumber, Integer pageSize,String sortBy,String sortStyle);
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortStyle);
    List<PostDTO> searchPost(String search);


}
