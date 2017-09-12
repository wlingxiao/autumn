package autumn.daily.support;

import autumn.common.NetUtils;
import autumn.daily.DailyResponse;
import autumn.daily.News;
import autumn.post.support.PageResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static autumn.common.ListUtils.subList;
import static autumn.daily.Constants.BASE_IMAGE_URL;

@RestController
@RequestMapping("/dailies")
@Slf4j
public class DailyController {

    private final static String IMAGE = "/api/v1/dailies/image/";

    private TitleService titleService;

    private NewsService newsService;


    private DailyHttpService dailyHttpService;

    @Autowired
    public DailyController(TitleService titleService, NewsService newsService, DailyHttpService dailyHttpService) {
        this.titleService = titleService;
        this.newsService = newsService;
        this.dailyHttpService = dailyHttpService;
    }

    @GetMapping
    public PageResponse<DailyResponse> loadPostPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        val pageP = titleService.pageTitle(page, 20, Sort.Direction.DESC);
        List<DailyResponse> a = pageP.getContent().stream()
                .flatMap((x) -> x.getStories().stream()).map((z) -> {
                    val zhihuImageUrl = z.getImages().get(0);
                    val replacedImageUrl = IMAGE + zhihuImageUrl.substring(zhihuImageUrl.indexOf(".com/") + 5);
                    val news = newsService.loadByNewsId(z.getNewsId());
                    return new DailyResponse(z.getNewsId() + "", z.getTitle(), null, replacedImageUrl, generateSummary(news));
                }).collect(Collectors.toList());
        return new PageResponse<>(pageP.getTotalElements(), subList(a, 0, 10));
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

    private String generateSummary(News news) {
        return news.getBody().substring(20);
    }
}
