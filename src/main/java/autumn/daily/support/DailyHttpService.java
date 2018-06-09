package autumn.daily.support;

import autumn.daily.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import static autumn.daily.Constants.BASE_NEWS_URL;
import static autumn.daily.Constants.BEFORE_NEWS_URL;
import static autumn.daily.Constants.LATEST_NEWS_URL;

@Service
public class DailyHttpService {

    private RestTemplate restTemplate;

    @Autowired
    public DailyHttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<Content> fetchLatest() {
        return CompletableFuture.completedFuture(restTemplate.getForObject(getLatestNewsUrl(), Content.class));
    }

    @Async
    public CompletableFuture<Content> fetchNewsByDate(Date date) {
        return CompletableFuture.completedFuture(restTemplate.getForObject(getNewsDateUrl(date), Content.class));
    }

    @Async
    public CompletableFuture<Content> fetchNewsById(String id) {
        return CompletableFuture.completedFuture(restTemplate.getForObject(getNewsIdUrl(id), Content.class));
    }

    private String getLatestNewsUrl() {
        return LATEST_NEWS_URL;
    }

    private String getNewsDateUrl(Date date) {
        // TODO refactor
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        return BEFORE_NEWS_URL + sd.format(date);
    }

    private String getNewsIdUrl(String id) {
        return BASE_NEWS_URL + id;
    }
}
