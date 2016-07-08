import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.wxapi.process.MediaType;
import com.vanke.wxapi.process.WxApi;
import com.vanke.wxapi.process.WxApiClient;
import com.vanke.wxapi.vo.Material;
import com.vanke.wxapi.vo.MaterialArticle;
import com.vanke.wxapi.vo.MaterialNews;
import com.vanke.wxapi.vo.MaterialUpdateNews;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class WxApiTest {

    /**
     * 新增临时素材
     * success
     */
    @Test
    public void testUploadMedia() {
        String mediaId = uploadMedia();
        System.out.println("mediaId = " + mediaId);
    }

    private String uploadMedia() {
        String uri = "http://172.16.0.205:8079/weixinPic/upload1/20160705/64621467689589302.png";
        String accessToken = TestUtil.getAcessToken();
        String uploadMediaUrl = WxApi.getUploadMediaUrl(accessToken, MediaType.Image.toString());
        //媒体文件上传后，获取时的唯一标识
        JSONObject jsonObject = WxApi.uploadMedia(uploadMediaUrl, uri);
        String media_id = (String) jsonObject.get("media_id");
        return media_id;
    }

    /**
     * 获取临时素材
     * todo:error,乱码,待解决
     */
    @Test
    public void testGetGet() {
        String accessToken = TestUtil.getAcessToken();
        String media_id = "M6Sw23ICv4poKhdsjEJf4nJV-sb6M5aHuFRsn65lGt02MdkTBK4QPK_oTbrJxTSA";
        String url = WxApi.getGet(accessToken, media_id);
        JSONObject jsonObject = WxApi.httpsRequest(url, "GET", null);
        System.out.println("WxApiTest.testGetGet");
    }

    /**
     * 新增永久图文素材
     * success
     */
    @Test
    public void testAddNews() {
        String accessToken = TestUtil.getAcessToken();
        String url = WxApi.getAddNews(accessToken);
        MaterialArticle materialArticle = new MaterialArticle();
        materialArticle.setTitle("钱威的图文素材2");
        materialArticle.setThumb_media_id("2JyScEeMJaSNPORQ2Jtk2GrzvFqeHTS_5PGVHLQPL2s");
        materialArticle.setAuthor("钱威的图文素材2");
        materialArticle.setDigest("钱威的图文素材2");
        materialArticle.setShow_cover_pic("1");
        materialArticle.setContent("钱威的图文素材2");
        materialArticle.setContent_source_url("");
        MaterialArticle materialArticle2 = new MaterialArticle();
        materialArticle2.setTitle("钱威的图文素材3");
        materialArticle2.setThumb_media_id("2JyScEeMJaSNPORQ2Jtk2Pdxwb5EAg5XsZFD_Jl7y84");
        materialArticle2.setAuthor("钱威的图文素材3");
        materialArticle2.setDigest("钱威的图文素材3");
        materialArticle2.setShow_cover_pic("0");
        materialArticle2.setContent("钱威的图文素材3");
        materialArticle2.setContent_source_url("");
        List<MaterialArticle> articles = new ArrayList<>();
        articles.add(materialArticle);
        articles.add(materialArticle2);
        MaterialNews materialNews = new MaterialNews();
        materialNews.setArticles(articles);
        String materialStr = JSON.toJSONString(materialNews);
        JSONObject jsonObject = WxApi.httpsRequest(url, "POST", materialStr);
        //返回的即为新增的图文消息素材的media_id。
        String media_id = (String) jsonObject.get("media_id");
        System.out.println("media_id = " + media_id);
    }

    /**
     * 新增永久图片素材
     * success
     */
    @Test
    public void testAddMaterialPic() {
//        String uri = "http://172.16.0.205:8079/weixinPic/upload1/20160705/64621467689589302.png";
        String uri = "http://172.16.0.205:8079/weixinPic/upload1/20160704/66101467616048235.png";
        String accessToken = TestUtil.getAcessToken();
        String uploadMediaUrl = WxApi.getAddMaterial(accessToken, MediaType.Image.toString());
        JSONObject jsonObject = WxApi.uploadMedia(uploadMediaUrl, uri);
        String media_id = (String) jsonObject.get("media_id");
        String url = (String) jsonObject.get("url");
        System.out.println("media_id=" + media_id + "|" + "url=" + url);
    }

    /**
     * 新增永久视频素材
     * todo 待处理
     */
    @Test
    public void testAddMaterialVideo() {
//        String uri = "http://172.16.0.205:8079/weixinPic/upload1/20160705/64621467689589302.png";
        String uri = "http://172.16.0.205:8079/weixinFile/upload/20160706/15491467772231562.mp4";
        String accessToken = TestUtil.getAcessToken();
        String uploadMediaUrl = WxApi.getAddMaterial(accessToken, MediaType.Video.toString());
        JSONObject jsonObject = WxApi.uploadMedia(uploadMediaUrl, uri);
    }

    /**
     * 获取永久图文素材信息
     * success
     */
    @Test
    public void testGetMaterialNews() {
        String mediaId = "WvWd-LZ_87HlImqRhVscb_CXOge1TmQPHfN1CivUCc8";
        String accessToken = TestUtil.getAcessToken();
        String url = WxApi.getMaterial(accessToken);
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("media_id", mediaId);
        String body = bodyObj.toString();
        JSONObject jsonObject = WxApi.httpsRequest(url, "POST", body);
        System.out.println("WxApiTest.testGetMaterial");
    }

    /**
     * 删除素材接口
     *  "errcode":ERRCODE,
     *  "errmsg":ERRMSG
     *  正常情况下调用成功时，errcode将为0。
     *  success
     */
    @Test
    public void testDeleteMaterial() {
        String mediaId = "WvWd-LZ_87HlImqRhVscb_8WaxwA3X9taKUjycrKYIU";
        String accessToken = TestUtil.getAcessToken();
        String url = WxApi.getDelMaterial(accessToken);
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("media_id", mediaId);
        String body = bodyObj.toString();
        JSONObject jsonObject = WxApi.httpsRequest(url, "POST", body);

    }

    /**
     * 修改素材接口
     * success
    */
    @Test
    public void testUpdateNews() {
        String mediaId = "WvWd-LZ_87HlImqRhVscb_CXOge1TmQPHfN1CivUCc8";
        String index = "0";
        String accessToken = TestUtil.getAcessToken();
        String url = WxApi.getUpdateNews(accessToken);
        MaterialArticle materialArticle = new MaterialArticle();
        materialArticle.setTitle("永久图文素材标题5");
        materialArticle.setThumb_media_id("WvWd-LZ_87HlImqRhVscb9_4sGo0NuUab-_k6rGKQcQ");
        materialArticle.setAuthor("永久图文素材作者5");
        materialArticle.setDigest("永久图文素材摘要5");
        materialArticle.setShow_cover_pic("1");
        materialArticle.setContent("永久图文素材内容5");
        materialArticle.setContent_source_url("");
        MaterialUpdateNews materialUpdateNews = new MaterialUpdateNews();
        materialUpdateNews.setArticles(materialArticle);
        materialUpdateNews.setMedia_id(mediaId);
        materialUpdateNews.setIndex(index);
        String materialStr = JSON.toJSONString(materialUpdateNews);
        JSONObject jsonObject = WxApi.httpsRequest(url, "POST", materialStr);
        // 返回说明 { "errcode":ERRCODE, "errmsg":ERRMSG } 正确时errcode的值应为0。
        System.out.println("WxApiTest.testUpdateNews");
    }

    /**
     * 获取素材总数
     * success
     */
    @Test
    public void testGetMaterialCount() {
        String accessToken = TestUtil.getAcessToken();
        String url = WxApi.getMaterialcount(accessToken);
        JSONObject jsonObject = WxApi.httpsRequest(url, "POST", null);
        System.out.println("WxApiTest.testGetMaterialCount");
    }

    /**
     * 获取永久素材列表
     * 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     */
    @Test
    public void testBatchgetMaterial() {
        String accessToken = TestUtil.getAcessToken();
        String httpUrl = WxApi.getBatchMaterialUrl(accessToken);
        JSONObject jsonObject = getMaterialList(MediaType.Image.toString(),httpUrl);
        List<JSONObject> itemList = (List) jsonObject.get("item");
        System.out.println("----------永久素材图片列表-------------");
        for (int i = 0; i < itemList.size(); i++) {
            JSONObject item = itemList.get(i);
            String name = (String) item.get("name");
            int update_time = (int) item.get("update_time");
            String media_id = (String) item.get("media_id");
            String url = (String) item.get("url");
            System.out.println("update_time=" + update_time + "|" + "name=" + name + "|" + "media_id=" + media_id + "|" + "url=" + url);
        }
        System.out.println("----------永久素材图片列表-------------");
        jsonObject = getMaterialList(MediaType.News.toString(),httpUrl);
        itemList = (List) jsonObject.get("item");
        System.out.println("----------永久素材图文列表-------------");
        for (int i = 0; i < itemList.size(); i++) {
            JSONObject item = itemList.get(i);
            String media_id = (String) item.get("media_id");
            int update_time = (int) item.get("update_time");
            JSONObject contentObject = (JSONObject) item.get("content");
            List<JSONObject> newsItemList = (List<JSONObject>) contentObject.get("news_item");
            for (int j = 0; j < newsItemList.size(); j++) {
                JSONObject news_item = newsItemList.get(j);
                String content = (String) news_item.get("content");
                String author = (String) news_item.get("author");
                String title = (String) news_item.get("title");
                String thumb_media_id = (String) news_item.get("thumb_media_id");
                String thumb_url = (String) news_item.get("thumb_url");
                int show_cover_pic = (int) news_item.get("show_cover_pic");
                String content_source_url = (String) news_item.get("content_source_url");
                String digest = (String) news_item.get("digest");
                String url = (String) news_item.get("url");
                String str =
                        "media_id=" + media_id + "|" +
                                "update_time=" + update_time + "|" +
                                "content=" + content + "|" +
                                "author=" + author + "|" +
                                "title=" + title + "|" +
                                "thumb_media_id=" + thumb_media_id + "|" +
                                "thumb_url=" + thumb_url + "|" +
                                "show_cover_pic=" + show_cover_pic + "|" +
                                "content_source_url=" + content_source_url + "|" +
                                "digest=" + digest + "|" +
                                "url=" + url;
                System.out.println(str);
            }
        }
        System.out.println("----------永久素材图文列表-------------");

    }

    private JSONObject getMaterialList(String type,String httpUrl) {
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("type", type);
        bodyObj.put("offset", 0);
        bodyObj.put("count", 10);
        String body = bodyObj.toString();
        JSONObject jsonObject = WxApi.httpsRequest(httpUrl, "POST", body);
        return jsonObject;
    }
}
