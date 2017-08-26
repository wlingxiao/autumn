package autumn.daily.support;

import autumn.daily.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    private NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News loadByNewsId(Integer newsId) {
        return newsRepository.findByNewsId(newsId);
    }

}
