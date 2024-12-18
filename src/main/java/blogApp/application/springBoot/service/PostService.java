package blogApp.application.springBoot.service;

import blogApp.application.springBoot.payload.PostDto;
import blogApp.application.springBoot.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePostById(PostDto postDto,long id);

    void deletePostById(long id);

    List<PostDto> getPostByCategoryId(Long categoryId);
}
