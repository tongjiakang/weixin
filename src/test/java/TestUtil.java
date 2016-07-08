import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.wxapi.process.WxApiClient;

/**
 * Created by Administrator on 2016/7/7.
 */
public class TestUtil {

    public static String getAcessToken() {
        AccountVo accountVo = new AccountVo();
//        accountVo.setAppid("wx0c2fa1ca62bd7f2a");
//        accountVo.setEncodingAESKey("d047af62a1a11967a5618bde55787fb8");

//        accountVo.setAppid("wx729843d7b1ff169e");
//        accountVo.setEncodingAESKey("141755864b7a4506a6e6d9f78dc35dfe");

        accountVo.setAppid("wx60cc1e01059063e5");
        accountVo.setEncodingAESKey("98a1dfda4586bca8c9abe8193d507207");

        String accessToken = WxApiClient.getAccessToken(accountVo);
        return accessToken;
    }

}
