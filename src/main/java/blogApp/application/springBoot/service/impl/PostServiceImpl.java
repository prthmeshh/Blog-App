package blogApp.application.springBoot.service.impl;

import blogApp.application.springBoot.entity.Category;
import blogApp.application.springBoot.entity.Post;
import blogApp.application.springBoot.exception.ResourceNotFoundException;
import blogApp.application.springBoot.payload.PostDto;
import blogApp.application.springBoot.payload.PostResponse;
import blogApp.application.springBoot.repository.CategoryRepository;
import blogApp.application.springBoot.repository.PostRepository;
import blogApp.application.springBoot.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
        );

        Post post=mapToPost(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        PostDto postResponse = mapToDto(newPost);

        return postResponse;

    }

    //Convert Post to PostDto
    private PostDto mapToDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }

    //Convert PostDto to Post
    private Post mapToPost(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Page<Post> posts = postRepository.findAll(PageRequest.of(pageNo, pageSize,sort));
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post postById = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        return mapToDto(postById);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        Post postById = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        postById.setTitle(postDto.getTitle());
        postById.setDescription(postDto.getDescription());
        postById.setContent(postDto.getContent());
        postById.setCategory(category);
        Post savedPost = postRepository.save(postById);
        return mapToDto(savedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );
        List<Post> postsByCategoryId = postRepository.findByCategoryId(categoryId);
        List<PostDto> collect = postsByCategoryId.stream().map(
                (posts) -> modelMapper.map(posts, PostDto.class)
        ).collect(Collectors.toList());
        return collect;

    }
}
