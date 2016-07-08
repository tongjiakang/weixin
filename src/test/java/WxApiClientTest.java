import com.vanke.mhj.service.basic.AccountService;
import com.vanke.wxapi.process.MediaType;
import com.vanke.wxapi.process.MpAccount;
import com.vanke.wxapi.process.WxApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/6/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class WxApiClientTest {

    @Resource
    private AccountService accountService;

    @Test
    public void testSyncBatchMaterial() {
        MpAccount mpAccount = new MpAccount();
        mpAccount.setAppid("wx0c2fa1ca62bd7f2a");
        mpAccount.setAppsecret("d047af62a1a11967a5618bde55787fb8");
        WxApiClient.syncBatchMaterial(MediaType.News, 1, 1, mpAccount);
    }
}
