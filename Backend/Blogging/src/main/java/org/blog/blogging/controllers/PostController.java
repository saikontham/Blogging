package org.blog.blogging.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.blog.blogging.config.AppConstants;
import org.blog.blogging.payloads.ApiResponse;
import org.blog.blogging.payloads.PostDTO;
import org.blog.blogging.payloads.PostResponse;
import org.blog.blogging.services.FileService;
import org.blog.blogging.services.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    @Value("${file.images.path}")
    private String path;

    private final PostService postService;

    private final FileService fileService;


    @PostMapping("/customer/{customerId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@PathVariable Integer customerId,
                                              @PathVariable Integer categoryId,
                                              @Valid @RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO, customerId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Integer postId,
                                              @Valid @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Deleted", true), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Integer postId) {
        PostDTO post = postService.getPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortStyle", defaultValue = AppConstants.SORT_DIR, required = false) String sortStyle
    ) {
        PostResponse posts = postService.getListOfPosts(pageNumber, pageSize, sortBy, sortStyle);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer customerId,
                                                        @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                        @RequestParam(value = "sortStyle", defaultValue = AppConstants.SORT_DIR, required = false) String sortStyle
    ) {
        PostResponse posts = postService.getPostsByUser(customerId,pageNumber,pageSize,sortBy,sortStyle);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
                                                            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                            @RequestParam(value = "sortStyle", defaultValue = AppConstants.SORT_DIR, required = false) String sortStyle) {
        PostResponse posts = postService.getPostsByCategory(categoryId,pageNumber,pageSize,sortBy,sortStyle);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<List<PostDTO>> searchPost(@PathVariable String search) {
        List<PostDTO> posts = postService.searchPost("%"+search+"%");
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadImage(
            @RequestParam("image")MultipartFile file,
            @PathVariable Integer postId

            ) throws IOException {
        PostDTO postDTO = postService.getPost(postId);
        String uploadedImage = fileService.UploadImage(path, file);
        postDTO.setImage(uploadedImage);
        PostDTO updatePost = postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }
    @GetMapping(value = "image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    )throws IOException
    {
        InputStream inputStream=fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());

    }
}
