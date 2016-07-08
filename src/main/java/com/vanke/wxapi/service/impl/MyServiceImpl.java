package com.vanke.wxapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.dao.basic.CustomMenuDao;
import com.vanke.mhj.dao.basic.KeyWordDao;
import com.vanke.mhj.dao.basic.NewsTemplateDao;
import com.vanke.mhj.dao.basic.UnKnowWordDao;
import com.vanke.mhj.dao.material.MsgNewsDao;
import com.vanke.mhj.model.basic.TCustomMenu;
import com.vanke.mhj.model.basic.TKeyWord;
import com.vanke.mhj.model.basic.TUnKnowWord;
import com.vanke.mhj.model.material.MsgNews;
import com.vanke.mhj.model.material.NewsItem;
import com.vanke.mhj.model.material.NewsTemplate;
import com.vanke.mhj.utils.oConvertUtils;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.mhj.vo.material.MsgNewsVo;
import com.vanke.wxapi.process.MsgType;
import com.vanke.wxapi.process.MsgXmlUtil;
import com.vanke.wxapi.process.WxMessageBuilder;
import com.vanke.wxapi.service.MyService;
import com.vanke.wxapi.vo.Article;
import com.vanke.wxapi.vo.MsgRequest;
import com.vanke.wxapi.vo.MsgResponseNews;
import com.vanke.wxapi.vo.MsgResponseText;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 业务消息处理
 * 开发者根据自己的业务自行处理消息的接收与回复；
 */

@Service
public class MyServiceImpl implements MyService{

	@Resource
	private KeyWordDao keyWordDao;
	@Resource
	private MsgNewsDao msgNewsDao;
	@Resource
	private UnKnowWordDao unKnowWordDao;
	@Resource
	private NewsTemplateDao newsTemplateDao;
	@Resource
	private CustomMenuDao customMenuDao;
	@Value("${domain}")
	private String domain;
	@Value("${weixinPic}")
	private String weixinPic;

	@Override
	public String processMsg(MsgRequest msgRequest, AccountVo accountVo,String projectName) {
		try {
			// 发送方帐号（open_id）
			String fromUserName = msgRequest.getFromUserName();
			// 公众帐号
			String toUserName = msgRequest.getToUserName();
			//接收到的消息类型
			String msgType = msgRequest.getMsgType();
			String msgId = msgRequest.getMsgId();
			//消息内容
			String content = msgRequest.getContent();
			System.out.println("------------微信客户端发送请求---------------------");
			System.out.println("fromUserName = " + fromUserName);
			System.out.println("toUserName = " + toUserName);
			System.out.println("msgType = " + msgType);
			System.out.println("msgId = " + msgId);
			System.out.println("content = " + content);

			String respXml = null;//返回的内容；
			if (msgType.equals(MsgType.Text.toString())) {
				//文本消息
				System.out.println("【微信触发类型】文本消息");
				respXml = this.processTextMsg(msgRequest, accountVo,projectName);
			} else if (msgType.equals(MsgType.Event.toString())) {
				//事件消息
				System.out.println("【微信触发类型】事件消息");
				respXml = this.processEventMsg(msgRequest);
			} else if (msgType.equals(MsgType.Image.toString())) {
				//图片消息
			} else if (msgType.equals(MsgType.Location.toString())) {
				//地理位置消息
			}
			return respXml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public JSONObject publishMenu(String gid,AccountVo accountVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject deleteMenu(AccountVo accountVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean syncAccountFansList(AccountVo accountVo) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 文本消息，一般公众号接收到的都是此类型消息
	 * @param msgRequest
	 * @param accountVo
	 * @return
	 */
	private String processTextMsg(MsgRequest msgRequest,AccountVo accountVo,String projectName){
		System.out.println("MyServiceImpl.processTextMsg");
		String content = msgRequest.getContent();
		if(!StringUtils.isEmpty(content)){
			//文本消息
			int i = 0;
			boolean hasKeyWork; //是否有关键字
			TKeyWord keyWord = null; //关键字
			TUnKnowWord unKnowWord = null; //未识别回复语
			//根据关键字名称获取关键字列表中的第一个
			String tmpContent = content.trim();//关键字名称
			List<TKeyWord> tKeyWordList = keyWordDao.getKeyWordsByName(tmpContent);
			String msgType = "";
			if (tKeyWordList != null && tKeyWordList.size() > 0) {
				//有关键字
				hasKeyWork = true;
				TKeyWord tKeyWord = tKeyWordList.get(0);
				keyWord = tKeyWord;
				msgType = tKeyWord.getMsgType();
			}else{
				//没有关键字,根据appid查找未识别回复语
				hasKeyWork = false;
				TUnKnowWord tUnKnowWord = unKnowWordDao.getByAppid(accountVo.getAppid());
				if (tUnKnowWord != null) {
					//设置了未识别回复语
					unKnowWord = tUnKnowWord;
					msgType = tUnKnowWord.getMsgType();
				}
			}
			//判断是文本信息还是图片信息
			if (msgType.equals(MsgType.Text.toString())) {
				//文本
				i = 1;
			} else if (msgType.equals(MsgType.News.toString())) {
				i = 2;
			}
			//根据关键词查询出来的结果，判断是用哪种回复格式
			if(i==1){//文本消息
				MsgNewsVo msgNewsVo = new MsgNewsVo();
				String msgNewsContent = "";
				if (hasKeyWork) {
					//有关键字的文本信息
					Long msgId = keyWord.getMsgId();
					MsgNews msgNews = msgNewsDao.find(msgId, MsgNews.class);
					msgNewsContent = msgNews.getContent();
				}else{
					//没有关键字的文本信息,查找是否有设置未识别回复语
					if (unKnowWord != null) {
						//有设置未识别回复语,获取文本内容
						Long msgId = unKnowWord.getMsgId();
						MsgNews msgNews = msgNewsDao.find(msgId, MsgNews.class);
						msgNewsContent = msgNews.getContent();
					}else{
						//没有设置未识别回复语
					}
				}
				msgNewsVo.setContent(msgNewsContent);
				return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, msgNewsVo));
			}else if(i==2){//图文消息
				if (hasKeyWork) {
					//有关键字的图片信息
					Long msgId = keyWord.getMsgId();
					/*NewsTemplate newsTemplate = newsTemplateDao.find(msgId, NewsTemplate.class);
					Set<NewsItem> newsItemList = newsTemplate.getNewsItemList();*/
					List<NewsItem> newsItemList = this.getArticles(msgId);
					List<Article> articleList = new ArrayList<>();
					for (NewsItem news : newsItemList) {
						String title = news.getTitle();
						String picUrl = domain + news.getImagePath();
						String description = news.getDescription();
						Long id = news.getId();
						String url = "";
						if (oConvertUtils.isEmpty(news.getUrl())) {
//							url = domain + "/picMaterial/goContent?id="+ id;
							url = domain;
							if (projectName != null && !"".equals(projectName)) {
								url += "/" + projectName;
							}
							url += "/picMaterial/goContent?id="+ id;
						} else {
							url = news.getUrl();
						}
						System.out.println("id = " + id);
						System.out.println("title = " + title);
						System.out.println("picUrl = " + picUrl);
						System.out.println("description = " + description);
						System.out.println("url = " + url);

						Article article = new Article();
						article.setTitle(title);
						article.setPicUrl(picUrl);
						article.setUrl(url);
						article.setDescription(description);
						articleList.add(article);
					}
					MsgResponseNews newsResp = new MsgResponseNews();
					newsResp.setCreateTime(new Date().getTime());
					newsResp.setFromUserName(msgRequest.getToUserName());
					newsResp.setToUserName(msgRequest.getFromUserName());
					newsResp.setMsgType(MsgType.News.toString());
					newsResp.setArticleCount(newsItemList.size());
					newsResp.setArticles(articleList);
//					respMessage = MessageUtil.newsMessageToXml(newsResp);
					String newsToXml = MsgXmlUtil.newsToXml(newsResp);
					System.out.println("newsToXml = " + newsToXml);
					return newsToXml;
				}else{
					//没有关键字
				}
//				List<NewsItem> newsItemList = new ArrayList<>();
//				return MsgXmlUtil.newsToXml(WxMessageBuilder.getMsgResponseNews(msgRequest, newsItemList));
			}else{
				MsgNewsVo msgNewsVo = new MsgNewsVo();
				return MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, msgNewsVo));
			}
		}
		return null;
	}

	/**
	 * 用户订阅公众账号、点击菜单按钮的时候，会触发事件消息
	 * @param msgRequest
	 * @return
	 */
	private String processEventMsg(MsgRequest msgRequest) throws Exception {
		String eventKey = msgRequest.getEventKey();
		System.out.println("eventKey = " + eventKey);
		String event = msgRequest.getEvent();
		System.out.println("event = " + event);
		if(MsgType.SUBSCRIBE.toString().equals(event)){//订阅消息
			
		}else if(MsgType.UNSUBSCRIBE.toString().equals(event)){//取消订阅消息
			
		}else{//点击事件消息
			System.out.println("点击事件");
			String str = doMyMenuEvent(msgRequest, null, "", msgRequest.getToUserName(), msgRequest.getFromUserName(), null, null, null);
			System.out.println("str = " + str);
			return str;
		}
		return null;
	}

	@Override
	public List<NewsItem> getArticles(long parseLong) {
		NewsTemplate newsTemplate =	newsTemplateDao.find(parseLong, NewsTemplate.class);
		List<NewsItem> items = new ArrayList<NewsItem>(newsTemplate.getNewsItemList());
		Collections.sort(items, new Comparator<NewsItem>() {
			@Override
			public int compare(NewsItem o1,NewsItem o2) {
				return o1.getOrders().compareTo(o2.getOrders());
			}
		});
		return items;
	}

	String doMyMenuEvent(MsgRequest msgRequest, MsgResponseText textMessage , String respMessage
			, String toUserName, String fromUserName, String respContent, String sys_accountId, HttpServletRequest request) throws Exception{
//		String key = requestMap.get("EventKey");
		System.out.println("MyServiceImpl.doMyMenuEvent");
		String key = msgRequest.getEventKey();
		System.out.println("key = " + key);
		//自定义菜单CLICK类型
		TCustomMenu menuEntity = customMenuDao.findByMenuKey(key);
		Long msgId = menuEntity.getMsgId();
		if (menuEntity != null&& oConvertUtils.isNotEmpty(msgId)) {
			String type = menuEntity.getMsgType();
			if (MsgType.Text.toString().equals(type)) {
				System.out.println("text");
				/*TextTemplate textTemplate = this.textTemplateService.getEntity(TextTemplate.class, menuEntity.getTemplateId());
				String content = textTemplate.getContent();
				textMessage.setContent(content);
				respMessage = MessageUtil.textMessageToXml(textMessage);*/
				MsgNewsVo msgNewsVo = new MsgNewsVo();
				msgNewsVo.setContent("尽请期待");
				respMessage = MsgXmlUtil.textToXml(WxMessageBuilder.getMsgResponseText(msgRequest, msgNewsVo));
			} else if (MsgType.News.toString().equals(type)) {
				System.out.println("news");
				List<NewsItem> newsList = getArticles(msgId);
				List<Article> articleList = new ArrayList<Article>();
//				NewsTemplate newsTemplate = newsTemplateService.getEntity(NewsTemplate.class, menuEntity.getTemplateId());
				for (NewsItem news : newsList) {
					Article article = new Article();
//					article.setTitle(news.getTitle());
					article.setPicUrl( domain + "/" + news.getImagePath());
					String url = "";
					String newsUrl = news.getUrl();
					System.out.println("newsUrl = " + newsUrl);
					if (oConvertUtils.isEmpty(newsUrl)) {
						System.out.println("1");
						if (key.equals("baoxiu")) {
							article.setTitle("客服报修");
							url = "http://221.224.53.27:8079/mhj-wechat/views/customServiceFix/customServiceFixApply.jsp?openId=" + fromUserName;
						} else if (key.equals("zixun")) {
							article.setTitle("资讯活动");
							url = "http://221.224.53.27:8079/mhj-wechat/index.jsp?openId=" + fromUserName;
						}
					} else {
						System.out.println("2");
						url = news.getContent();
					}
					System.out.println("url = " + url);
					article.setUrl(url);
//					article.setDescription(news.getContent());
					article.setDescription("");
					articleList.add(article);
				}
				MsgResponseNews newsResp = new MsgResponseNews();
				newsResp.setCreateTime(new Date().getTime());
				newsResp.setFromUserName(toUserName);
				newsResp.setToUserName(fromUserName);
				newsResp.setMsgType(MsgType.News.toString());
				newsResp.setArticleCount(newsList.size());
				newsResp.setArticles(articleList);
				respMessage = MsgXmlUtil.newsToXml(newsResp);
			} else if ("expand".equals(type)) {
				/*WeixinExpandconfigEntity expandconfigEntity = weixinExpandconfigService.getEntity(WeixinExpandconfigEntity.class,menuEntity.getTemplateId());
				String className = expandconfigEntity.getClassname();
				KeyServiceI keyService = (KeyServiceI) Class.forName(className).newInstance();
				respMessage = keyService.excute("", textMessage,request);*/
			}
		}
		return respMessage;
	}
}


