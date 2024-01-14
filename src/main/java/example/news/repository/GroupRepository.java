package example.news.repository;

import example.news.entity.CommentEntity;
import example.news.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    //List<GroupEntity> findAllByNews(Long news);
}
