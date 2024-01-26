package example.news.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jdk.jfr.Timespan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto extends AbstractDto{
    @Positive
    private Long id;
    @NotNull
    @Size(min = 2, max = 500)
    private String content;

    private Timestamp createDate;

    private UserDto user;
}
