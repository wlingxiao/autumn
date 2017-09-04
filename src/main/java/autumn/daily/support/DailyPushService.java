package autumn.daily.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

class DailyPushService {

    private Logger LOG = LoggerFactory.getLogger(DailyPushService.class);

    final static String DAILY_TOPIC_ENDPOINT = "/topic/daily/news";

    private SimpMessagingTemplate simpMessagingTemplate;

    public DailyPushService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void pushNews(Object obj) {
        LOG.debug(obj.toString());
        simpMessagingTemplate.convertAndSend(DAILY_TOPIC_ENDPOINT, obj);
    }

}
