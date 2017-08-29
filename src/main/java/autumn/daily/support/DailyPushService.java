package autumn.daily.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class DailyPushService {

    final static String DAILY_TOPIC_ENDPOINT = "/topic/daily/news";

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public DailyPushService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void pushNews(Object obj) {
        simpMessagingTemplate.convertAndSend(DAILY_TOPIC_ENDPOINT, obj);
    }

}
