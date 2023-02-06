package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PostMapping
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping
    public List<PostDto> getAllPosts(){
        List<PostDto> postDto = postService.getAllPosts();
        return postDto; //or you can place return in above post service by removing list and reference variable

    }

    //http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);
    return ResponseEntity.ok(dto);
    // or - // return new  ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/1
    //update post by id Rest API
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id")long id){
        PostDto dto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
   public ResponseEntity<String> deletePostById(@PathVariable("id")long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully!!", HttpStatus.OK);
    }
}
