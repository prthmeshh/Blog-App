package blogApp.application.springBoot.service;

import blogApp.application.springBoot.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postsId,CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(long postsId);

    CommentDto getCommentByCommentId(long postId,long commentId);

    CommentDto updateCommentByCommentId(long postId,long commentId,CommentDto commentDto);

    void deleteCommentByCommentId(long postId,long commentId);
}
