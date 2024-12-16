package blogApp.application.springBoot.service.impl;

import blogApp.application.springBoot.entity.Comment;
import blogApp.application.springBoot.entity.Post;
import blogApp.application.springBoot.exception.BlogAPIException;
import blogApp.application.springBoot.exception.ResourceNotFoundException;
import blogApp.application.springBoot.payload.CommentDto;
import blogApp.application.springBoot.repository.CommentRepository;
import blogApp.application.springBoot.repository.PostRepository;
import blogApp.application.springBoot.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postsId) {
        List<Comment> comments = commentRepository.findByPostId(postsId);
        return comments.stream().map(comment -> (maptoDto(comment))).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentByCommentId(long postId, long commentId){
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        return maptoDto(comment);
    }

    @Override
    public CommentDto updateCommentByCommentId(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment save = commentRepository.save(comment);

        return maptoDto(save);

    }

    @Override
    public void deleteCommentByCommentId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        commentRepository.deleteById(commentId);

    }

    private CommentDto maptoDto(Comment comment){

        return modelMapper.map(comment,CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto,Comment.class);
    }
}
