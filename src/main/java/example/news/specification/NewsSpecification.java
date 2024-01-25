package example.news.specification;

import example.news.entity.NewsEntity;
import example.news.filter.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {
        static Specification<NewsEntity> byNewsNameAndOwnerIdFilter(NewsFilter filter) {

            return Specification.where(byNewsName(filter.getNewsName()))
                    .and(byNewsGroupName(filter.getGroupName()))
                    .and(byNewsUserId(filter.getNewsUserId()));
        }

        static Specification<NewsEntity> byNewsUserId(Long newsUserId) {
            return (root, query, cb) -> {
                if (newsUserId == null) {
                    return null;
                }
                return cb.equal(root.get("user").get("id"), newsUserId);
            };
        }

        static Specification<NewsEntity> byNewsGroupName(String groupName) {
            return (root, query, cb) -> {
                if (groupName == null) {
                    return null;
                }
                return cb.equal(root.get("category").get("name"), groupName);
            };
        }

        static Specification<NewsEntity> byNewsName(String newsName) {
            return (root, query, cb) -> {
                if (newsName == null) {
                    return null;
                }
                return cb.equal(root.get("newsName"), newsName);
            };
        }
}
