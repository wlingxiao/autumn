package autumn.daily.support;

import autumn.daily.Title;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends MongoRepository<Title, String> {

}
