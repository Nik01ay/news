package example.news.dto;

import example.news.entity.GroupEntity;
import example.news.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto  {
    private Long id;
    private String title;
    private String content;
    private Timestamp createDate;
    private Integer countComments;
    private Long userId;
    private Long groupId;
    private String userName;
    private String groupTitle;

}
