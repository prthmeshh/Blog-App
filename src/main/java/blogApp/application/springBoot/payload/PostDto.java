package blogApp.application.springBoot.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "Post DTO model information"
)
public class PostDto {

    private Long id;
    @NotNull
    @Size(min=2,message = "Post title should be at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min=2,message = "Post description should have atleast 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    private long categoryId;

}
