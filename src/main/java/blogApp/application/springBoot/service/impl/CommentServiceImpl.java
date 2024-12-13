package blogApp.application.springBoot.service.impl;

import blogApp.application.springBoot.entity.Comment;
import blogApp.application.springBoot.entity.Post;
import blogApp.application.springBoot.exception.ResourceNotFoundException;
import blogApp.application.springBoot.payload.CommentDto;
import blogApp.application.springBoot.repository.CommentRepository;
import blogApp.application.springBoot.repository.PostRepository;
import blogApp.application.springBoot.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @Override
    public CommentDto createComment(long postsId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postsId));

        comment.setPost(post);
        Comment commentSaved = commentRepository.save(comment);

        return maptoDto(commentSaved);

    }

    private CommentDto maptoDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
