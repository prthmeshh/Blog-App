package blogApp.application.springBoot.repository;

import blogApp.application.springBoot.entity.Category;
import blogApp.application.springBoot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByCategoryId(Long categoryId);
}
