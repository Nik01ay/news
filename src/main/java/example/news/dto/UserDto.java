package example.news.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.news.entity.CommentEntity;
import example.news.entity.NewsEntity;
import example.news.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements InterfaceDto {
    @Positive
    private Long id;
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @JsonIgnore
    private String password;

    private Set<String> roles;

    private Set<String> permission;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Доступ только для записи
    public String getPassword() {
        return password;
    }

}
