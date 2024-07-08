package org.blog.blogging.services;

import lombok.RequiredArgsConstructor;
import org.blog.blogging.entities.Category;
import org.blog.blogging.entities.Customer;
import org.blog.blogging.entities.Post;
import org.blog.blogging.exceptions.CustomerNotFoundException;
import org.blog.blogging.exceptions.ResourceNotFoundException;
import org.blog.blogging.payloads.PostDTO;
import org.blog.blogging.payloads.PostResponse;
import org.blog.blogging.repositories.CategoryRepository;
import org.blog.blogging.repositories.CustomerRepository;
import org.blog.blogging.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer customerId, Integer categoryId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(format("Customer with Id %s Not Present", customerId)));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(format("Category with Id %s Not Present", categoryId)));
        Post post = modelMapper.map(postDTO, Post.class);
        post.setImage("");
        post.setAddedDate(new Date());
        post.setCustomer(customer);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(format("Post with Id %s not found", postId)));
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setImage(postDTO.getImage());
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost,PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(format("Post with Id %s not found", postId)));
        postRepository.delete(post);
    }

    @Override
    public PostDTO getPost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(format("Post with Id %s not found", postId)));
        return modelMapper.map(post,PostDTO.class);
    }

    @Override
    public PostResponse getListOfPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortStyle) {
        Sort sort=sortStyle.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(pageable);
        return pagableMapper(pagePost);
    }

    @Override
    public PostResponse getPostsByUser(Integer customerId,Integer pageNumber, Integer pageSize,String sortBy,String sortStyle) {
        Sort sort=sortStyle.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(format("Customer with Id %s Not Present", customerId)));

        Page<Post> pagePost = postRepository.findByCustomer(customer, pageable);

        return pagableMapper(pagePost);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortStyle) {

        Sort sort=sortStyle.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException(format("Category with Id %s Not Found",categoryId)));
        Page<Post> pagePost = postRepository.findByCategory(category, pageable);

        return pagableMapper(pagePost);
    }

    private PostResponse pagableMapper(Page<Post> pagePost)
    {
        List<PostDTO> postDTOS = pagePost.getContent().stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setLastPage(pagePost.isLast());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements((int) pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        return postResponse;
    }
    @Override
    public List<PostDTO> searchPost(String search) {
        List<Post> titleContaining = postRepository.searchByTitle(search);
        List<PostDTO> postDTOStream = titleContaining.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return postDTOStream;
    }
}
