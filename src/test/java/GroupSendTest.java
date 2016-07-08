import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.wxapi.process.WxApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by PanJM on 2016/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class GroupSendTest {

    @Test
    private void test() {
        String accessToken = TestUtil.getAcessToken();
        String url = "";



//        JSONObject jsonObject = WxApi.httpsRequest(url, "POST", );
    }
}
