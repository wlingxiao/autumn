package autumn.daily.support;

import autumn.common.NetUtils;
import autumn.daily.DailyResponse;
import autumn.daily.News;
import autumn.post.support.PageResponse;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static autumn.daily.Constants.BASE_IMAGE_URL;

@RestController
@RequestMapping("/dailies")
public class TitleController {

    private final static Logger LOG = LoggerFactory.getLogger(TitleController.class);

    private final static String IMAGE = "/api/v1/dailies/image/";

    private TitleService titleService;

    private NewsService newsService;


    private DailyHttpService dailyHttpService;

    @Autowired
    public TitleController(TitleService titleService, NewsService newsService, DailyHttpService dailyHttpService) {
        this.titleService = titleService;
        this.newsService = newsService;
        this.dailyHttpService = dailyHttpService;
    }

    @GetMapping(value = "")
    public PageResponse<DailyResponse> loadPostPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                    @RequestParam(value = "direction", required = false) Integer direction) {
        val pageP = titleService.pageTitle(page, 20, Sort.Direction.DESC);
        List<DailyResponse> a = pageP.getContent().stream()
                .flatMap((x) -> x.getStories().stream()).map((z) -> {
                    val l = z.getImages().get(0);
                    val f = IMAGE + l.substring(l.indexOf(".com/") + 5);
                    val news = newsService.loadByNewsId(9095858);
                    return new DailyResponse(z.getNewsId() + "", z.getTitle(), null, f, news.getTitle());
                }).collect(Collectors.toList());
        return new PageResponse<>(pageP.getTotalElements(), a.subList(0, 10));
    }

    @GetMapping(value = "/news/{newsId}")
    public ResponseEntity<?> loadById(@PathVariable("newsId") Integer newsId) {
        val news = newsService.loadByNewsId(newsId);
        if (news == null) {
            dailyHttpService.fetchNewsById(newsId.toString())
                    .thenAcceptAsync(x -> newsService.save(x));
            return ResponseEntity.noContent().build();
        }
        news.setBody(news.getBody().replaceAll("http://pic.+com/", IMAGE));
        return ResponseEntity.ok(news);
    }


    @ResponseBody
    @RequestMapping("/image/{imageId}")
    public ResponseEntity<byte[]> loadImage(@PathVariable("imageId") String imageId) throws IOException {
        return ResponseEntity.ok(NetUtils.fetchImage(BASE_IMAGE_URL + imageId));
    }
}
