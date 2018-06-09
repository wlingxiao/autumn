package autumn;

import autumn.daily.Content;
import autumn.daily.Title;
import autumn.daily.support.ContentRepository;
import autumn.daily.support.TitleRepository;
import autumn.user.User;
import autumn.user.support.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static autumn.common.DateTimeUtil.now;

@Component
@Profile("dev")
@Slf4j
public class StartupRunner implements CommandLineRunner {
    private UserRepository userRepository;

    private TitleRepository titleRepository;

    private ContentRepository contentRepository;

    @Autowired
    public StartupRunner(UserRepository userRepository, TitleRepository titleRepository, ContentRepository contentRepository) {
        this.userRepository = userRepository;
        this.titleRepository = titleRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        log.debug("insert admin test data");
        User user = new User("admin", "admin@admin.com", "111111", now(), now());
        userRepository.save(user);

        val title = new Title();
        title.setPubDate(20171231);
        title.setImages("https://pic4.zhimg.com/v2-ae1075a9fc31a6e04b3696526aa8d21b.jpg");
        title.setType((short) 0);
        title.setPubId(9663456L);
        title.setGaPrefix("123122");
        title.setTitle("小事·你有哪些新年愿望？");

        titleRepository.save(title);

        val content = new Content();
        content.setPubId(9663456L);
        content.setSummary("11111111");
        content.setBody("1222222222222222222222222222222222224444444444444444444444444444444444444444444444444444444");
        contentRepository.save(content);

    }
}
