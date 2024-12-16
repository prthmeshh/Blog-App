package blogApp.application.springBoot.controller;

import blogApp.application.springBoot.payload.CommentDto;
import blogApp.application.springBoot.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postsId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postsId") long postsId
            , @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>
                (commentService.createComment(postsId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{postsId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(value="postsId") long postsId){
        return new ResponseEntity<>(commentService.getAllCommentsByPostId(postsId),HttpStatus.OK);
    }

    @GetMapping("/{postsId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable(value = "postsId") long postsId,
                                                            @PathVariable(value = "id") long id){

        return new ResponseEntity<>(commentService.getCommentByCommentId(postsId,id),HttpStatus.OK);
    }

    @PutMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentByCommentId(@PathVariable(value = "postId") long postId,
                                                               @PathVariable(value="id") long id,
                                                               @Valid @RequestBody CommentDto
                                                               commentDto){
        return new ResponseEntity<>(commentService.updateCommentByCommentId(postId,id,commentDto),HttpStatus.OK);
    }

    @DeleteMapping("/{postsId}/comments/{id}")
    public ResponseEntity<String> deleteCommentByCommentId(@PathVariable(value="postsId") long postsId,
                                                               @PathVariable(value = "id") long id){
        commentService.deleteCommentByCommentId(postsId,id);
        return new ResponseEntity<>("Comment deleted Sucessfully",HttpStatus.OK);
    }


}
