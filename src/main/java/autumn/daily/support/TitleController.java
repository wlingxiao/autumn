package autumn.daily.support;

import autumn.daily.DailyResponse;
import autumn.daily.News;
import autumn.post.support.PageResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dailies")
public class TitleController {

    private TitleService titleService;

    private NewsService newsService;

    @Autowired
    public TitleController(TitleService titleService, NewsService newsService) {
        this.titleService = titleService;
        this.newsService = newsService;
    }

    @GetMapping(value = "")
    public PageResponse<DailyResponse> loadPostPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                    @RequestParam(value = "direction", required = false) Integer direction) {
        val pageP = titleService.pageTitle(page, 20, Sort.Direction.DESC);
        List<DailyResponse> a = pageP.getContent().stream()
                .flatMap((x) -> {
                    return x.getStories().stream();
                }).map((z) -> {
                    val l = z.getImages().get(0);
                    val f = "/api/v1/image/" + l.substring(l.indexOf(".com/") + 5);
                    val news = newsService.loadByNewsId(9095858);
                    return new DailyResponse(z.getNewsId() + "", z.getTitle(), null, f, news.getTitle());
                }).collect(Collectors.toList());
        return new PageResponse<>(pageP.getTotalElements(), a.subList(0, 10));
    }

    @GetMapping(value = "/{newsId}")
    public News loadById(@PathVariable("newsId") Integer newsId) {
        return newsService.loadByNewsId(newsId);
    }
}
