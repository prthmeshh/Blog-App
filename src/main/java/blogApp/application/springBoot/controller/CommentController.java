package blogApp.application.springBoot.controller;

import blogApp.application.springBoot.payload.CommentDto;
import blogApp.application.springBoot.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postsId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postsId") long postsId
            , @RequestBody CommentDto commentDto){
        return new ResponseEntity<>
                (commentService.createComment(postsId,commentDto), HttpStatus.CREATED);
    }


}
