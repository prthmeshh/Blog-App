package blogApp.application.springBoot.repository;

import blogApp.application.springBoot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
