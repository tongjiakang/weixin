package com.vanke.wxapi.process;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.utils.CacheUtils;
import com.vanke.mhj.utils.contants.CommonContants;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.wxapi.vo.Material;
import com.vanke.wxapi.vo.MaterialArticle;
import com.vanke.wxapi.vo.MaterialItem;
import com.vanke.wxapi.vo.TemplateMessage;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 微信 客户端，统一处理微信相关接口
 */
public class WxApiClient {

	public static Map<String, AccessToken> getAccessTokenCache() {
		Map<String, AccessToken> accessTokenMap = (Map<String, AccessToken>) CacheUtils.get(CommonContants.ACCESS_TOKEN);
		if (accessTokenMap == null) {
			accessTokenMap = new Hashtable<>();
		}
		return accessTokenMap;
	}

	public static String getAccessToken(AccountVo accountVo) {
		MpAccount mpAccount = new MpAccount();
		mpAccount.setAppid(accountVo.getAppid());
		mpAccount.setAppsecret(accountVo.getEncodingAESKey());
		String accessToken = getAccessToken(mpAccount);
		return accessToken;
	}

	/**
	 * 获取AccessToken
	 * @param mpAccount
	 * @return
     */
	public static String getAccessToken(MpAccount mpAccount){
		String appid = mpAccount.getAppid();
		String appsecret = mpAccount.getAppsecret();
		//获取唯一的accessToken，如果是多账号，请自行处理
		AccessToken token = WxMemoryCacheClient.getSingleAccessToken(appid);
		if(token != null && !token.isExpires()){//不为空，并且没有过期
			return token.getAccessToken();
		}else{
			token = WxApi.getAccessToken(appid, appsecret);
			if(token != null){
				if(token.getErrcode() != null){//获取失败
					System.out.println("## getAccessToken Error = " + token.getErrmsg());
				}else{
					WxMemoryCacheClient.addAccessToken(appid, token);
					return token.getAccessToken();
				}
			}
			return null;
		}
	}
	
	//获取jsTicket
	public static String getJSTicket(MpAccount mpAccount){
		//获取唯一的JSTicket，如果是多账号，请自行处理
		JSTicket jsTicket = WxMemoryCacheClient.getSingleJSTicket();
		if(jsTicket != null && !jsTicket.isExpires()){//不为空，并且没有过期
			return jsTicket.getTicket();
		}else{
			String token = getAccessToken(mpAccount);
			jsTicket = WxApi.getJSTicket(token);
			if(jsTicket != null){
				if(jsTicket.getErrcode() != null){//获取失败
					System.out.println("## getJSTicket Error = " + jsTicket.getErrmsg());
				}else{
					WxMemoryCacheClient.addJSTicket(mpAccount.getAccount(), jsTicket);
					return jsTicket.getTicket();
				}
			}
			return null;
		}
	}
	
	//获取OAuthAccessToken
	public static OAuthAccessToken getOAuthAccessToken(MpAccount mpAccount,String code){
		//获取唯一的accessToken，如果是多账号，请自行处理
		OAuthAccessToken token = WxMemoryCacheClient.getSingleOAuthAccessToken();
		if(token != null && !token.isExpires()){//不为空，并且没有过期
			return token;
		}else{
			token = WxApi.getOAuthAccessToken(mpAccount.getAppid(),mpAccount.getAppsecret(),code);
			if(token != null){
				if(token.getErrcode() != null){//获取失败
					System.out.println("## getOAuthAccessToken Error = " + token.getErrmsg());
				}else{
					token.setOpenid(null);//获取OAuthAccessToken的时候设置openid为null；不同用户openid缓存
					WxMemoryCacheClient.addOAuthAccessToken(mpAccount.getAccount(), token);
					return token;
				}
			}
			return null;
		}
	}
	
	//获取openId
	public static String getOAuthOpenId(MpAccount mpAccount,String code){
		OAuthAccessToken token = WxApi.getOAuthAccessToken(mpAccount.getAppid(),mpAccount.getAppsecret(),code);
		if(token != null){
			if(token.getErrcode() != null){//获取失败
				System.out.println("## getOAuthAccessToken Error = " + token.getErrmsg());
			}else{
				return token.getOpenid();
			}
		}
		return null;
	}
	
	//发布菜单
	public static JSONObject publishMenus(String menus,MpAccount mpAccount){
		String accessToken = getAccessToken(mpAccount);
		String url = WxApi.getMenuCreateUrl(accessToken);
		return WxApi.httpsRequest(url, HttpMethod.POST, menus);
	}
	
	//创建个性化菜单
	public static JSONObject publishAddconditionalMenus(String menus,MpAccount mpAccount){
		String accessToken = getAccessToken(mpAccount);
		String url = WxApi.getMenuAddconditionalUrl(accessToken);
		return WxApi.httpsRequest(url, HttpMethod.POST, menus);
	}
	
	//删除菜单
	public static JSONObject deleteMenu(MpAccount mpAccount){
		String accessToken = getAccessToken(mpAccount);
		String url = WxApi.getMenuDeleteUrl(accessToken);
		return WxApi.httpsRequest(url, HttpMethod.POST, null);
	}
	
	
	/**
	 * 获取素材
	 * @param mediaType 素材类型
	 * @param offset 开始位置
	 * @param count 获取数量
	 * @return
	 */
	public static Material syncBatchMaterial(MediaType mediaType, Integer offset, Integer count,MpAccount mpAccount){
		String accessToken = getAccessToken(mpAccount);
		String url = WxApi.getBatchMaterialUrl(accessToken);
		JSONObject bodyObj = new JSONObject();
		bodyObj.put("type", mediaType.toString());
		bodyObj.put("offset", offset);
		bodyObj.put("count", count);
		String body = bodyObj.toString();
		try {
			JSONObject jsonObj = WxApi.httpsRequest(url, "POST", body);
			if (jsonObj.containsKey("errcode")) {//获取素材失败
				System.out.println(ErrCode.errMsg(jsonObj.getIntValue("errcode")));
				return null;
			}else{
				Material material = new Material();
				material.setTotalCount(jsonObj.getIntValue("total_count"));
				material.setItemCount(jsonObj.getIntValue("item_count"));
				JSONArray arr = jsonObj.getJSONArray("item");
				if(arr != null && arr.size() > 0){
					List<MaterialItem> itemList = new ArrayList<MaterialItem>();
					for(int i = 0; i < arr.size(); i++){
						JSONObject item = arr.getJSONObject(i);
						MaterialItem materialItem = new MaterialItem();
						materialItem.setMediaId(item.getString("media_id"));
						materialItem.setUpdateTime(item.getLong("update_time")*1000L);
						if(item.containsKey("content")){//mediaType=news （图文消息）
							JSONArray articles = item.getJSONObject("content").getJSONArray("news_item");
							List<MaterialArticle> newsItems = new ArrayList<MaterialArticle>();
							for(int j = 0; j < articles.size(); j++){
								JSONObject article = articles.getJSONObject(j);
								MaterialArticle ma = new MaterialArticle();
								ma.setTitle(article.getString("title"));
								ma.setThumb_media_id(article.getString("thumb_media_id"));
								ma.setShow_cover_pic(article.getString("show_cover_pic"));
								ma.setAuthor(article.getString("author"));
								ma.setContent_source_url(article.getString("content_source_url"));
								ma.setContent(article.getString("content"));
								ma.setUrl(article.getString("url"));
								newsItems.add(ma);
							}
							materialItem.setNewsItems(newsItems);
						}else{
							materialItem.setName(item.getString("name"));
							materialItem.setUrl(item.getString("url"));
						}
						itemList.add(materialItem);
					}
					material.setItems(itemList);
				}
				return material;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据openid群发接口
	 * @param mediaId：素材的id；通过素材管理,或者上传素材获取
	 * @param msgType
	 * @param mpAccount
	 * @return
	 */
	public static JSONObject massSendByOpenIds(List<String> openids,String mediaId,MsgType msgType,MpAccount mpAccount){
		if(openids != null && openids.size() > 0){
			JSONObject postObj = new JSONObject();
			JSONObject media = new JSONObject();
			postObj.put("touser", openids);
			media.put("media_id", mediaId);
			postObj.put(msgType.toString(), media);
			postObj.put("msgtype", msgType.toString());
			String accessToken = getAccessToken(mpAccount);
			return WxApi.httpsRequest(WxApi.getMassSendUrl(accessToken), HttpMethod.POST, postObj.toString());
		}
		return null;
	}
	
	/**
	 * 根据openid群发文本消息
	 * @param openids
	 * @param content
	 * @param mpAccount
	 * @return
	 */
	public static JSONObject massSendTextByOpenIds(List<String> openids,String content, MpAccount mpAccount){
		if(openids != null && openids.size() > 0){
			if(openids.size() == 1){//根据openId群发，size至少为2
				openids.add("1");
			}
			String[] arr = (String[])openids.toArray(new String[openids.size()]);
			JSONObject postObj = new JSONObject();
			JSONObject text = new JSONObject();
			postObj.put("touser", arr);
			text.put("content", content);
			postObj.put("text", text);
			postObj.put("msgtype", MsgType.Text.toString());
			String accessToken = getAccessToken(mpAccount);
			return WxApi.httpsRequest(WxApi.getMassSendUrl(accessToken), HttpMethod.POST, postObj.toString());
		}
		return null;
	}
	
	/**
	 * 发送客服消息
	 * @param openid
	 * @param content 消息内容
	 * @return
	 */
	public static JSONObject sendCustomTextMessage(String openid,String content,MpAccount mpAccount){
		if(!StringUtils.isBlank(openid) && !StringUtils.isBlank(content)){
			String accessToken = getAccessToken(mpAccount);
			content = WxMessageBuilder.prepareCustomText(openid, content);
			return WxApi.httpsRequest(WxApi.getSendCustomMessageUrl(accessToken), HttpMethod.POST, content);
		}
		return null;
	}
	
	/**
	 * 发送模板消息
	 * @return
	 */
	public static JSONObject sendTemplateMessage(TemplateMessage tplMsg,MpAccount mpAccount){
		if(tplMsg != null){
			String accessToken = getAccessToken(mpAccount);
			return WxApi.httpsRequest(WxApi.getSendTemplateMessageUrl(accessToken), HttpMethod.POST, tplMsg.toString());
		}
		return null;
	}
	
	/**
	 * 创建临时二维码
	 * @param expireSecodes 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	 * @param scene 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000)
	 * @return
	 */
	public static byte[] createQRCode(Integer expireSecodes, Integer scene, MpAccount mpAccount){
		if(scene != null){
			String accessToken = getAccessToken(mpAccount);
			String postBody = WxApi.getQrcodeJson(expireSecodes, scene);
			JSONObject jsObj = WxApi.httpsRequest(WxApi.getCreateQrcodeUrl(accessToken),HttpMethod.POST, postBody);
			if(jsObj != null){
				String ticket = jsObj.getString("ticket");
				if(!StringUtils.isBlank(ticket)){
					return WxApi.httpsRequestByte(WxApi.getShowQrcodeUrl(ticket), HttpMethod.GET);
				}
				return null;
			}
		}
		return null;
	}
	
	//创建永久字符串二维码
	public static byte[] createQRCodeLimit(String qrcodeStr, MpAccount mpAccount){
		if(!StringUtils.isBlank(qrcodeStr)){
			String accessToken = getAccessToken(mpAccount);
			String postBody = WxApi.getQrcodeLimitJson(qrcodeStr);
			JSONObject jsObj = WxApi.httpsRequest(WxApi.getCreateQrcodeUrl(accessToken),HttpMethod.POST, postBody);
			if(jsObj != null){
				String ticket = jsObj.getString("ticket");
				if(!StringUtils.isBlank(ticket)){
					jsObj = WxApi.httpsRequest(WxApi.getShowQrcodeUrl(ticket), HttpMethod.GET);
					
				}
				return null;
			}
		}
		return null;
	}
	
	
}



