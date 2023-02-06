package com.myblog.service;

import com.myblog.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    public void  deletePostById(long id);
}
