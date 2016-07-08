package com.vanke.wxapi.controller;

import com.alibaba.fastjson.JSON;
import com.vanke.mhj.service.basic.AccountService;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.wxapi.process.MsgXmlUtil;
import com.vanke.wxapi.service.MyService;
import com.vanke.wxapi.util.wx.SignUtil;
import com.vanke.wxapi.vo.MsgRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 微信与开发者服务器交互接口
 */
@Controller
@RequestMapping("/wxapi")
public class WxApiController {
	
	@Resource
	private MyService myService;
	
	@Resource
	private AccountService accountService;
	
	/**
	 * GET请求：进行URL、Tocken 认证；
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * @throws IOException 
	 */
	@RequestMapping(value = "/{appid}/message",  method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response,@PathVariable String appid) throws IOException {
		AccountVo accountVo = accountService.getAccountByAppid(appid);
		PrintWriter out = response.getWriter();
		if(accountVo != null){
			String token = accountVo.getToken();//获取token，进行验证；
			String signature = request.getParameter("signature");// 微信加密签名
			String timestamp = request.getParameter("timestamp");// 时间戳
			String nonce = request.getParameter("nonce");// 随机数
			String echostr = request.getParameter("echostr");// 随机字符串
			// 校验成功返回  echostr，成功成为开发者；否则返回error，接入失败
			if (SignUtil.validSign(signature, token, timestamp, nonce)) {
				out.print(echostr);
			}
		}
		out.flush();
		out.close();
//		return "error";
	}
	
	/**
	 * POST 请求：进行消息处理；
	 * */
	@RequestMapping(value = "/{appid}/message", method = RequestMethod.POST)
	@ResponseBody
	public String doPost(HttpServletRequest request,@PathVariable String appid,HttpServletResponse response) {
		//处理用户和微信公众账号交互消息
		AccountVo accountVo = accountService.getAccountByAppid(appid);
		try {
			MsgRequest msgRequest = MsgXmlUtil.parseXml(request);//获取发送的消息
			System.out.println("获取消息："+JSON.toJSONString(msgRequest).toString());
			String string = myService.processMsg(msgRequest,accountVo,getProjectName(request));
			System.out.println("返回消息："+string);
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private String getProjectName(HttpServletRequest request) {
		String projectPath = request.getSession().getServletContext().getRealPath("");
		String rootPath = "";
		try {
			projectPath = projectPath.replace('\\', '/');
			rootPath = projectPath.substring(projectPath.lastIndexOf("/") + 1,projectPath.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootPath;
	}
}




