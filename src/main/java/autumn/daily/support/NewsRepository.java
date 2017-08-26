package autumn.daily.support;

import autumn.daily.News;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends MongoRepository<News, String> {
    News findByNewsId(Integer id);
}
