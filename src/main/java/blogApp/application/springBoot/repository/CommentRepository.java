package blogApp.application.springBoot.repository;

import blogApp.application.springBoot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
