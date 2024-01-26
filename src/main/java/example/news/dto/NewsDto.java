package example.news.dto;

import example.news.entity.GroupEntity;
import example.news.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto  {
    @Positive
    private Long id;
    @NotNull
    @Size(min = 2, max = 150)
    private String title;
    @NotNull
    @Size(min = 2, max = 500)
    private String content;
    private Timestamp createDate;
    @PositiveOrZero
    private Integer countComments;
    @Positive
    private Long userId;
    @Positive
    private Long groupId;
    @NotNull
    @Size(min = 2, max = 50)
    private String userName;
    @NotNull
    @Size(min = 2, max = 150)
    private String groupTitle;

}
