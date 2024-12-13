package blogApp.application.springBoot.service;

import blogApp.application.springBoot.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(long postsId,CommentDto commentDto);
}
