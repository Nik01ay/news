package example.news.repository;

import example.news.entity.CommentEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

  //  List<CommentEntity> findAllByNews(Long news, Pageable pageable);
    List<CommentEntity> findAllByNewsId(Long newsId);
    List<CommentEntity> findAllByUserId(Long userId, Pageable pageable);
    List<CommentEntity> findAllByUserId(Long userId);

    List<CommentEntity> findAll();
}
