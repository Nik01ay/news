package example.news.dto;

import lombok.*;

import java.util.List;




@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsWithCommentsDto {
    private NewsDto newsDto;
    private List<CommentDto> commentDtoList;

}
