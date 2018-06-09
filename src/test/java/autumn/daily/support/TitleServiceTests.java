package autumn.daily.support;

import lombok.val;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TitleServiceTests {

    @Autowired
    private TitleService titleService;

    @Test
    @Ignore
    public void testPage() {
        val a = titleService.pageTitle(1, 10, Sort.Direction.DESC);
        System.out.println(a.getSize());
    }

}
