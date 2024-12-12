package blogApp.application.springBoot.service;

import blogApp.application.springBoot.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();

    PostDto getPostById(long id);

    PostDto updatePostById(PostDto postDto,long id);

    void deletePostById(long id);
}
