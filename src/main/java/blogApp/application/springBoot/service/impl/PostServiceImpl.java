package blogApp.application.springBoot.service.impl;

import blogApp.application.springBoot.entity.Post;
import blogApp.application.springBoot.exception.ResourceNotFoundException;
import blogApp.application.springBoot.payload.PostDto;
import blogApp.application.springBoot.repository.PostRepository;
import blogApp.application.springBoot.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post=mapToPost(postDto);

        Post newPost = postRepository.save(post);

        PostDto postResponse = mapToDto(newPost);

        return postResponse;

    }

    //Convert Post to PostDto
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;

    }

    //Convert PostDto to Post
    private Post mapToPost(PostDto postDto){
        Post newPost = new Post();
        newPost.setTitle(postDto.getTitle());
        newPost.setContent(postDto.getContent());
        newPost.setDescription(postDto.getDescription());
        return newPost;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo,int pageSize) {

        Page<Post> posts = postRepository.findAll(PageRequest.of(pageNo, pageSize));
        List<Post> listOfPosts = posts.getContent();

        return listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post postById = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        return mapToDto(postById);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        Post postById = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        postById.setTitle(postDto.getTitle());
        postById.setDescription(postDto.getDescription());
        postById.setContent(postDto.getContent());
        Post savedPost = postRepository.save(postById);
        return mapToDto(savedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}
