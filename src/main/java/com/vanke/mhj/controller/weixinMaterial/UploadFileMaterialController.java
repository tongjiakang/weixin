package com.vanke.mhj.controller.weixinMaterial;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.service.basic.AccountService;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.material.MsgLinkVo;
import com.vanke.wxapi.process.MediaType;
import com.vanke.wxapi.process.WxApi;
import com.vanke.wxapi.process.WxApiClient;
import com.vanke.wxapi.vo.FileMaterial;

/**
 * @author qianwei
 *
 */
@Controller
@RequestMapping(value = "/uploadFileMaterial")
public class UploadFileMaterialController extends BaseController {

	@Resource
	private AccountService accountService;

	@RequestMapping("/list")
	public String list() {
		return "weixin/weixinMaterial/uploadFileMaterial/uploadFileMaterialList";
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "weixin/weixinMaterial/uploadFileMaterial/uploadFileMaterialAdd";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Json save(MultipartHttpServletRequest request) throws IOException {

		MultipartFile multipartFile = request.getFile("uploadFile");
		// 素材类型
		String type = request.getParameter("type");
		// accountid
		Long accountid = Long.parseLong(request.getParameter("accountId"));

		// 素材名称
		String fileName = multipartFile.getOriginalFilename();

		// 素材contentType
		String contentType = multipartFile.getContentType();

		InputStream inputStream = multipartFile.getInputStream();

		// 获取token
		String accessToken = WxApiClient.getAccessToken(accountService.getAccount(accountid));

		// 获取上传素材api地址
		String uploadMediaUrl = WxApi.getAddMaterial(accessToken, type);

		JSONObject result = WxApi.uploadMedia(uploadMediaUrl, fileName, contentType, inputStream);
		Json json = new Json();
		String message;
		if (result != null) {
			if (0 == result.getIntValue("errcode")) {
				json.setSuccess(true);
				message = "素材上传成功！";
			} else {
				json.setSuccess(false);
				message = "同素材上传失败！错误码为：" + result.getIntValue("errcode") + "错误信息为：" + result.getString("errmsg");
			}
		} else {
			json.setSuccess(false);
			message = "素材上传失败！";
		}
		json.setMsg(message);
		return json;
	}

	/**
	 * 列表数据
	 * 
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public PageModel dataGrid(HttpServletRequest request, Long accountId, String type, PageModel ph) {

		JSONObject bodyObj = new JSONObject();

		int offset = (ph.getPage()-1)*ph.getPageSize();
		bodyObj.put("type", type);
		bodyObj.put("offset", offset);
		bodyObj.put("count", ph.getPageSize());
		String body = bodyObj.toString();

		// 获取token
		String accessToken = WxApiClient.getAccessToken(accountService.getAccount(accountId));
		String url = WxApi.getBatchMaterialUrl(accessToken);
		JSONObject jsonObject = WxApi.httpsRequest(url, "POST", body);
		if (jsonObject.getIntValue("errcode")!=0) {
			return ph;
		}
		ph.setTotal(jsonObject.getLongValue("total_count"));
		JSONArray jsonArray = jsonObject.getJSONArray("item");
		List<FileMaterial> fileMaterials = JSON.parseArray(jsonArray.toJSONString(), FileMaterial.class);
		ph.setRows(fileMaterials);
		return ph;
	}
	
	/**
	 * 删除素材
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(HttpServletRequest request, Long accountId, String media_id) {
		Json json = new Json();
		String accessToken = WxApiClient.getAccessToken(accountService.getAccount(accountId));
		String url = WxApi.getDelMaterial(accessToken);
		JSONObject bodyObj = new JSONObject();
		bodyObj.put("media_id", media_id);
		JSONObject result = WxApi.httpsRequest(url, "POST", bodyObj.toJSONString());
		String message;
		if (0 == result.getIntValue("errcode")) {
			json.setSuccess(true);
			message = "素材删除成功！";
		} else {
			json.setSuccess(false);
			message = "素材删除失败！错误码为：" + result.getIntValue("errcode") + "错误信息为：" + result.getString("errmsg");
		}
		json.setMsg(message);
		return json;
	}

}
