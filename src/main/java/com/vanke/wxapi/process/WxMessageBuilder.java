package com.vanke.wxapi.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.alibaba.fastjson.JSONObject;
import com.vanke.mhj.model.material.NewsItem;
import com.vanke.mhj.vo.material.MsgNewsVo;
import com.vanke.wxapi.vo.Article;
import com.vanke.wxapi.vo.MsgRequest;
import com.vanke.wxapi.vo.MsgResponseNews;
import com.vanke.wxapi.vo.MsgResponseText;

/**
 * 消息builder工具类
 */
public class WxMessageBuilder {
	
	//客服文本消息
	public static String prepareCustomText(String openid,String content){
		JSONObject jsObj = new JSONObject();
		jsObj.put("touser", openid);
		jsObj.put("msgtype", MsgType.Text.name());
		JSONObject textObj = new JSONObject();
		textObj.put("content", content);
		jsObj.put("text", textObj);
		return jsObj.toString();
	}
	
	//获取 MsgResponseText 对象
	public static MsgResponseText getMsgResponseText(MsgRequest msgRequest,MsgNewsVo msgNewsVo){
		if(msgNewsVo != null){
			MsgResponseText reponseText = new MsgResponseText();
			reponseText.setToUserName(msgRequest.getFromUserName());
			reponseText.setFromUserName(msgRequest.getToUserName());
			reponseText.setMsgType(MsgType.Text.toString());
			reponseText.setCreateTime(new Date().getTime());
			reponseText.setContent(msgNewsVo.getContent());
			return reponseText;
		}else{
			return null;
		}
	}
	
	//获取 MsgResponseNews 对象
	public static MsgResponseNews getMsgResponseNews(MsgRequest msgRequest,List<NewsItem> newsItems){
		if(newsItems != null && newsItems.size() > 0){
			MsgResponseNews responseNews = new MsgResponseNews();
			responseNews.setToUserName(msgRequest.getFromUserName());
			responseNews.setFromUserName(msgRequest.getToUserName());
			responseNews.setMsgType(MsgType.News.toString());
			responseNews.setCreateTime(new Date().getTime());
			responseNews.setArticleCount(newsItems.size());
			List<Article> articles = new ArrayList<Article>(newsItems.size());
			for(NewsItem n : newsItems){
				Article a = new Article();
				a.setTitle(n.getTitle());
//				a.setPicUrl(n.getPicpath());
//				if(StringUtils.isEmpty(n.getFromurl())){
//					a.setUrl(n.getUrl());
//				}else{
//					a.setUrl(n.getFromurl());
//				}
//				a.setDescription(n.getBrief());
				articles.add(a);
			}
			responseNews.setArticles(articles);
			return responseNews;
		}else{
			return null;
		}
	}
	
}
