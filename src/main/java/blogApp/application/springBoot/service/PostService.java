package blogApp.application.springBoot.service;

import blogApp.application.springBoot.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
