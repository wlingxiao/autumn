package autumn.daily.support;

import autumn.daily.News;
import autumn.daily.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class DailyHttpService {

    private final static String BASE_NEWS_URL = "";

    private final static String BASE_TITLE_URL = "";

    private final static String LATEST_NEWS_URL = "";

    private RestTemplate restTemplate;

    @Autowired
    public DailyHttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<News> fetchLatest() {
        return CompletableFuture.completedFuture(restTemplate.getForObject(LATEST_NEWS_URL, News.class));
    }

    @Async
    public CompletableFuture<News> fetchNewsByDate(Date date) {
        String dataStr = null;
        return CompletableFuture.completedFuture(restTemplate.getForObject(BASE_NEWS_URL + dataStr, News.class));
    }

    @Async
    public CompletableFuture<Title> fetchNewsById(String id) {
        return CompletableFuture.completedFuture(restTemplate.getForObject(BASE_TITLE_URL + id, Title.class));
    }
}
