package blogApp.application.springBoot.repository;

import blogApp.application.springBoot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
