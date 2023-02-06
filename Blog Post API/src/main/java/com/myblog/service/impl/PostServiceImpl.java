package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFoundException;
import com.myblog.payload.PostDto;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
//here dependency injection performed using constructor based injection not @Autowired based injection
    private PostRepository postRepository;
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    @Override
    public PostDto createPost(PostDto postDto) {
      Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        PostDto newPostDto = mapToDto(newPost);
        return newPostDto;
    }

    Post mapToEntity(PostDto postDto) {
        Post post = new Post();

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto .getContent());
        return post;
    }
   PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
   }


    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return  posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        return mapToDto(post);
    }

    //update post by id Rest API
    @Override
    public PostDto updatePost(PostDto postDto, long id) {       //this method will take dto and id. based on id number it will search the post
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
         //once the post is found the below steps will update the post content present in it(PostDto postDto)
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);           //what it is saving it will return back as Entity object
        PostDto dto = mapToDto(updatedPost);                    //it will convert entity to dto
        return dto;                                             //or we can write return in front of postRepository in above to reduce the steps
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);     //another way to perform delete which takes or deletes by Entity


    }

}
