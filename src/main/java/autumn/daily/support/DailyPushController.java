package autumn.daily.support;

import autumn.daily.DailyResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import static autumn.daily.support.DailyPushService.DAILY_TOPIC_ENDPOINT;

@Controller
public class DailyPushController {

    private SimpMessagingTemplate simpMessagingTemplate;

    private DailyBackService dailyBackService;

    @Autowired
    public DailyPushController(SimpMessagingTemplate simpMessagingTemplate, DailyBackService dailyBackService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.dailyBackService = dailyBackService;
    }

    @MessageMapping("/daily")
    @SendTo(DAILY_TOPIC_ENDPOINT)
    public DailyResponse loadDailyBackground() throws Exception {
        val a = dailyBackService.laodDailyBackground();
        a.thenAcceptAsync((x) -> {
            simpMessagingTemplate.convertAndSend("/topic/daily", x);
        });
        val d = new DailyResponse();
        d.setTitle("lalalala");
        return d;
    }

}
