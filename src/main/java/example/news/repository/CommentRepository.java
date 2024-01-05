package example.news.repository;

import example.news.entity.CommentEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByNews(Long news, Pageable pageable);
    List<CommentEntity> findAllByNews(Long news);
    List<CommentEntity> findAllByUser(Long user, Pageable pageable);
    List<CommentEntity> findAllByUser(Long user);

    List<CommentEntity> findAll();
}
