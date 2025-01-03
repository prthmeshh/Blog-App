package blogApp.application.springBoot.controller;

import blogApp.application.springBoot.entity.Post;
import blogApp.application.springBoot.payload.PostDto;
import blogApp.application.springBoot.payload.PostResponse;
import blogApp.application.springBoot.repository.CategoryRepository;
import blogApp.application.springBoot.service.PostService;
import blogApp.application.springBoot.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String asc
    )
    {
        return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,asc),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto,@PathVariable long id){
        return new ResponseEntity<>(postService.updatePostById(postDto,id),HttpStatus.OK);
    }


    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/getPosts/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable Long id){
        List<PostDto> postByCategoryId = postService.getPostByCategoryId(id);
        return new ResponseEntity<>(postByCategoryId,HttpStatus.OK);
    }

}
