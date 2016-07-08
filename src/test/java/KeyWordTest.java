import com.vanke.mhj.dao.basic.KeyWordDao;
import com.vanke.mhj.model.basic.TKeyWord;
import com.vanke.mhj.model.material.NewsItem;
import com.vanke.wxapi.service.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class KeyWordTest {

    @Resource
    private KeyWordDao keyWordDao;
    @Resource
    private MyService myService;

    @Test
    public void testGetKeyWordsByName() {
        List<TKeyWord> hi = keyWordDao.getKeyWordsByName("hasdfi");
        TKeyWord tKeyWord = hi.get(0);
        System.out.println("KeyWordTest.testGetKeyWordsByName");
    }

    @Test
    public void test() {
        List<NewsItem> articles = myService.getArticles(5l);
        for (int i = 0; i < articles.size(); i++) {
            NewsItem newsItem = articles.get(i);
            System.out.println("newsItem = " + newsItem.getTitle());
        }
    }
    
    @Test
    public void test2() {
    	String aa = "&lt;p&gt;&lt;video class=&quot;edui-upload-video video-js vjs-default-skin&quot; controls=&quot;&quot; preload=&quot;none&quot; width=&quot;420&quot; height=&quot;280&quot; src=&quot;https://221.224.53.27/weixinFile/upload/20160705/87041467686534745.mp4&quot; data-setup=&quot;{}&quot;&gt;&lt;source src=&quot;https://221.224.53.27/weixinFile/upload/20160705/87041467686534745.mp4&quot; type=&quot;video/mp4&quot;/&gt;&lt;/video&gt;正文&lt;img src=&quot;https://221.224.53.27/weixinPic/upload1/20160704/66101467616048235.png&quot; title=&quot;9.png&quot; style=&quot;width: 321px; height: 154px;&quot;/&gt;&lt;/p&gt;&lt;p&gt;阿斯顿发生负担 &amp;nbsp;&amp;nbsp;&lt;img src=&quot;https://221.224.53.27/weixinPic/upload1/20160704/89851467618045438.png&quot; title=&quot;10.png&quot; style=&quot;width: 232px; height: 116px;&quot;/&gt;&lt;/p&gt;";
        System.out.println(HtmlUtils.htmlUnescape(aa));
    }
}
