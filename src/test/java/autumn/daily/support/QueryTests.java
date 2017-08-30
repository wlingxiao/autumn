package autumn.daily.support;

import autumn.daily.support.NewsRepository;
import autumn.daily.support.TitleRepository;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryTests {

    @Autowired
    private MongoOperations operations;

   /* @Test
    public void testQuery() {
        Query searchUserQuery = new Query(Criteria.where("_id").is("597c7e7cb56b8c0550a77e89"));
        val a = operations.find(searchUserQuery, Title.class, "daily_titles");
        a.forEach((x) -> {
            x.getStories().forEach((y) -> {
                y.getImages().forEach((z) -> {
                    System.out.println(z);
                });
            });
        });
    }*/

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void testQuery() {
        val a = titleRepository.findOne("597c7e7cb56b8c0550a77e89");
        System.out.println(a.getDate());
    }

    @Test
    public void testNews () {
        val a = newsRepository.findByNewsId(9095858);
        Assert.assertNotNull(a);
    }

}
