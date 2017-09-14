package autumn.daily.support;

import autumn.daily.News;
import autumn.daily.Title;
import com.google.gson.Gson;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DailyControllerTests {

    private final static String titleStr = "{\"date\":\"20170908\",\"stories\":[{\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-e25e6c75314b932b0cfa985457e1fd64.jpg\"],\"type\":0,\"id\":9604750,\"ga_prefix\":\"090809\",\"title\":\"后来，只要他们出现，人们便会说：「看啊，传奇的一代」\"},{\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-998113d05b23080cb442cbf937e41e5c.jpg\"],\"type\":0,\"id\":9606157,\"ga_prefix\":\"090808\",\"title\":\"「长官，这里发现智慧生物遗迹，他们称自己为……人类」\"},{\"images\":[\"https:\\/\\/pic3.zhimg.com\\/v2-bef0acef38b642275d979d8a8a2a90ba.jpg\"],\"type\":0,\"id\":9606116,\"ga_prefix\":\"090807\",\"title\":\"有人靠它出名，有人拿它起名，以后的游戏想要超越《王者荣耀》，很难\"},{\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-49bd526958c1d993f008fcde9e4ff955.jpg\"],\"type\":0,\"id\":9606837,\"ga_prefix\":\"090807\",\"title\":\"我这么胖，到底是因为吃得太多还是动得太少？\"},{\"title\":\"「企业做坏、政府买单」，于是涉嫌捕猎鲨鱼的中国渔船在南美被扣了\",\"ga_prefix\":\"090807\",\"images\":[\"https:\\/\\/pic3.zhimg.com\\/v2-e4437003d8a20b5eb34bc917ae734dfe.jpg\"],\"multipic\":true,\"type\":0,\"id\":9606765},{\"images\":[\"https:\\/\\/pic3.zhimg.com\\/v2-8a33424d9e30918b3b6ce47d679d2d02.jpg\"],\"type\":0,\"id\":9602162,\"ga_prefix\":\"090806\",\"title\":\"瞎扯 · 如何正确地吐槽\"}],\"top_stories\":[{\"image\":\"https:\\/\\/pic1.zhimg.com\\/v2-cceffc2e17185ae51b7b2d14b4414e84.jpg\",\"type\":0,\"id\":9606837,\"ga_prefix\":\"090807\",\"title\":\"我这么胖，到底是因为吃得太多还是动得太少？\"},{\"image\":\"https:\\/\\/pic3.zhimg.com\\/v2-2593c8c79ca5b7079dc01c189261ece2.jpg\",\"type\":0,\"id\":9606116,\"ga_prefix\":\"090807\",\"title\":\"有人靠它出名，有人拿它起名，以后的游戏想要超越《王者荣耀》，很难\"},{\"image\":\"https:\\/\\/pic3.zhimg.com\\/v2-d827cc7cd60b9438e9c15e33bfd2d1ea.jpg\",\"type\":0,\"id\":9606765,\"ga_prefix\":\"090807\",\"title\":\"「企业做坏、政府买单」，于是涉嫌捕猎鲨鱼的中国渔船在南美被扣了\"},{\"image\":\"https:\\/\\/pic3.zhimg.com\\/v2-99ce64fb4b296abe50a8e30ec5c320ae.jpg\",\"type\":0,\"id\":9606479,\"ga_prefix\":\"090719\",\"title\":\"PG One 到底能不能叫「万磁王」？两家粉丝都冷静下\"},{\"image\":\"https:\\/\\/pic3.zhimg.com\\/v2-fcf2e6e94943994d255d49e0da8d1136.jpg\",\"type\":0,\"id\":9601653,\"ga_prefix\":\"090717\",\"title\":\"没有邮箱、不用手机、总是揣个保温杯，你永远猜不透他想干什么\"}]}\n";

    private final static String newsStr = "{\"body\":\"<div class=\\\"main-wrap content-wrap\\\">\\n<div class=\\\"headline\\\">\\n\\n<div class=\\\"img-place-holder\\\"><\\/div>\\n\\n\\n\\n<\\/div>\\n\\n<div class=\\\"content-inner\\\">\\n\\n\\n\\n\\n<div class=\\\"question\\\">\\n<h2 class=\\\"question-title\\\">哪一个瞬间让你觉得没文化真可怕？<\\/h2>\\n\\n<div class=\\\"answer\\\">\\n\\n<div class=\\\"meta\\\">\\n<img class=\\\"avatar\\\" src=\\\"http:\\/\\/pic1.zhimg.com\\/d3a6e1490_is.jpg\\\">\\n<span class=\\\"author\\\">皮耶霍，<\\/span><span class=\\\"bio\\\">经济学学士历史学硕士法学博士<\\/span>\\n<\\/div>\\n\\n<div class=\\\"content\\\">\\n<p>他们不知道霍去病二十四岁就病死了么&hellip;&hellip;<\\/p>\\r\\n<p><img class=\\\"content-image\\\" src=\\\"http:\\/\\/pic3.zhimg.com\\/70\\/v2-54645281a6c3bb9e1a45f3cf4a9a06e6_b.jpg\\\" alt=\\\"\\\" \\/><\\/p>\\n<\\/div>\\n<\\/div>\\n\\n\\n<div class=\\\"view-more\\\"><a href=\\\"http:\\/\\/www.zhihu.com\\/question\\/37407915\\\">查看知乎讨论<span class=\\\"js-question-holder\\\"><\\/span><\\/a><\\/div>\\n\\n<\\/div>\\n\\n\\n\\n\\n\\n<div class=\\\"question\\\">\\n<h2 class=\\\"question-title\\\">有哪一刻让你觉得中年将至？<\\/h2>\\n\\n<div class=\\\"answer\\\">\\n\\n<div class=\\\"meta\\\">\\n<img class=\\\"avatar\\\" src=\\\"http:\\/\\/pic2.zhimg.com\\/0ca17ec19_is.jpg\\\">\\n<span class=\\\"author\\\">刘新征<\\/span>\\n<\\/div>\\n\\n<div class=\\\"content\\\">\\n<p>觉得工作是最有意思的事儿。<\\/p>\\n<\\/div>\\n<\\/div>\\n\\n\\n<div class=\\\"view-more\\\"><a href=\\\"http:\\/\\/www.zhihu.com\\/question\\/64907301\\\">查看知乎讨论<span class=\\\"js-question-holder\\\"><\\/span><\\/a><\\/div>\\n\\n<\\/div>\\n\\n\\n\\n\\n\\n<div class=\\\"question\\\">\\n<h2 class=\\\"question-title\\\">买菜这件事上北方和南方到底有多大区别？<\\/h2>\\n\\n<div class=\\\"answer\\\">\\n\\n<div class=\\\"meta\\\">\\n<img class=\\\"avatar\\\" src=\\\"http:\\/\\/pic3.zhimg.com\\/v2-7e867ed2af6da5319426546ac3b04982_is.jpg\\\">\\n<span class=\\\"author\\\">张宏伟，<\\/span><span class=\\\"bio\\\">医学工程<\\/span>\\n<\\/div>\\n\\n<div class=\\\"content\\\">\\n<p>只记得小时候，家里买肉就是买了半只猪回来，切成两斤一块，扔到院子里，用雪埋上，一到烧饭的时候妈妈就喊着让我们几个孩子到雪地里徒手刨肉，当然雪地里也不单只扒出猪肉，还有鸡、鹅、冻梨、冻豆腐&hellip;&hellip;一次妹妹被雪埋的一个猪头吓哭了。<\\/p>\\n<\\/div>\\n<\\/div>\\n\\n\\n<div class=\\\"view-more\\\"><a href=\\\"http:\\/\\/www.zhihu.com\\/question\\/64880896\\\">查看知乎讨论<span class=\\\"js-question-holder\\\"><\\/span><\\/a><\\/div>\\n\\n<\\/div>\\n\\n\\n\\n\\n\\n<div class=\\\"question\\\">\\n<h2 class=\\\"question-title\\\">你见过哪些特别销魂的老干体诗词？<\\/h2>\\n\\n<div class=\\\"answer\\\">\\n\\n<div class=\\\"meta\\\">\\n<img class=\\\"avatar\\\" src=\\\"http:\\/\\/pic1.zhimg.com\\/446df06ba31b084cff4a0831b72f7ef0_is.jpg\\\">\\n<span class=\\\"author\\\">南宫靖明，<\\/span><span class=\\\"bio\\\">飞面传教士(奶子才是正义)<\\/span>\\n<\\/div>\\n\\n<div class=\\\"content\\\">\\n<p>看到这个问题，我流下了屈辱的泪水。<\\/p>\\r\\n<p>&mdash;&mdash;题记<\\/p>\\r\\n<p>当年我们辽源矿务局老干部活动中心有这么一群大爷，其中有一位大爷特别有才。某日他诗兴大发，遂赋诗一首。<\\/p>\\r\\n<blockquote>\\r\\n<p><strong>致辽煤工人<\\/strong><\\/p>\\r\\n<p>炮声隆隆响彻天，<\\/p>\\r\\n<p>辽煤工人一致间。<\\/p>\\r\\n<p>我们都是打不倒的英雄汉。<\\/p>\\r\\n<p>坚决完成五个大会战，<\\/p>\\r\\n<p>坚决完成五！个！大！会！战！<\\/p>\\r\\n<\\/blockquote>\\r\\n<p><strong>而且还在一月后他七十大寿的时候，在酒席上把这首诗念了出来。局领导一听，卧槽，这首诗不错啊（麻痹，哪不错了）。决定全局学习。<\\/strong><\\/p>\\r\\n<p>过了一个月，大家都会背了，矿务局医院，矿务局各种子弟小学，矿务局职工，可能矿务局的野狗叫的时候都是按照这个频率叫的之后。又一位大爷横空出世，<strong>他给谱曲了！！！（麻痹，有才的大爷怎么那么多）。<\\/strong>于是我们局的夕阳红合唱团参加市里比赛的时候就唱了这首歌，还得奖了！局领导一看，卧槽这个更不错啊。于是一个月之后，矿务局医院，矿务局各种子弟小学，矿务局职工都会唱这首歌了，什么，你问野狗？野狗都恶心死了。那时候我小学，大家都知道小时候的记忆特别牢靠，于是我现在想忘都特么忘不了。<\\/p>\\r\\n<p>想听的人可以来深圳找我面基，也许我喝多了会唱给你听，当然了，更多可能是你被我打死。<\\/p>\\n<\\/div>\\n<\\/div>\\n\\n\\n<div class=\\\"view-more\\\"><a href=\\\"http:\\/\\/www.zhihu.com\\/question\\/34673952\\\">查看知乎讨论<span class=\\\"js-question-holder\\\"><\\/span><\\/a><\\/div>\\n\\n<\\/div>\\n\\n\\n<\\/div>\\n<\\/div>\",\"image_source\":\"《西游记》\",\"title\":\"瞎扯 · 如何正确地吐槽\",\"image\":\"https:\\/\\/pic2.zhimg.com\\/v2-58ff45455c5d79ae23d79d38aaf445a9.jpg\",\"share_url\":\"http:\\/\\/daily.zhihu.com\\/story\\/9602162\",\"js\":[],\"ga_prefix\":\"090806\",\"section\":{\"thumbnail\":\"https:\\/\\/pic3.zhimg.com\\/v2-8a33424d9e30918b3b6ce47d679d2d02.jpg\",\"id\":2,\"name\":\"瞎扯\"},\"images\":[\"https:\\/\\/pic3.zhimg.com\\/v2-8a33424d9e30918b3b6ce47d679d2d02.jpg\"],\"type\":0,\"id\":9602162,\"css\":[\"http:\\/\\/news-at.zhihu.com\\/css\\/news_qa.auto.css?v=4b3e3\"]}\n";

    private Gson gson = new Gson();

    @Mock
    private TitleService titleService;

    @Mock
    private Page<Title> titlePage;

    @Mock
    private NewsService newsService;

    @Test
    public void testLoadPostPage() {
        val title = gson.fromJson(titleStr, Title.class);
        val titles = new LinkedList<Title>();
        val news = gson.fromJson(newsStr, News.class);
        titles.add(title);
        given(titlePage.getContent())
                .willReturn(titles);
        given(titlePage.getTotalElements())
                .willReturn((long) titles.size());
        given(titleService.pageTitle(eq(1), anyInt(), eq(Sort.Direction.DESC)))
                .willReturn(titlePage);
        given(newsService.loadByNewsId(news.getNewsId())).willReturn(news);
        val titleController = new DailyController(titleService, newsService, null);
        val responses = titleController.loadPostPage(1);
        assertThat(responses.getCount()).isEqualTo(1);
        assertThat(responses.getData().get(0).getImage()).doesNotContain("zhimg.com");
    }
}
