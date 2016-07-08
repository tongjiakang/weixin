package com.vanke.mhj.controller.material;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.controller.base.BaseController;
import com.vanke.mhj.model.common.WeixinPic;
import com.vanke.mhj.model.material.NewsItem;
import com.vanke.mhj.model.material.NewsTemplate;
import com.vanke.mhj.service.base.BaseService;
import com.vanke.mhj.service.material.PicMaterialService;
import com.vanke.mhj.utils.AjaxJson;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.utils.oConvertUtils;
import com.vanke.mhj.vo.base.Json;
import com.vanke.mhj.vo.material.VNewsItem;
import com.vanke.mhj.vo.material.VPicNewsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 图文素材
 * 
 * @author qianwei
 *
 */
@Controller
@RequestMapping("/picMaterial")
public class PicMaterialController extends BaseController {

    @Autowired
    private BaseService baseService;

    @Autowired
    private PicMaterialService picMaterialService;

    @Value("${weixinPic}")
    private String weixinPic;

    @Value("${domain}")
    private String domain;

    /**
     * 列表展示
     * 
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("weixin/material/text/picMaterial");
    }

    /**
     * 列表数据
     * 
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public PageModel dataGrid(HttpServletRequest request, PageModel ph, VPicNewsTemplate vPicNewsTemplate) {
        return picMaterialService.dataGrid(ph, vPicNewsTemplate);
    }

    /**
     * 添加图文模板
     * 
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView addPage() {
        return new ModelAndView("weixin/material/text/picMaterialAdd");
    }

    /**
     * 添加图文模板
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public ModelAndView editPage(Long id, HttpServletRequest request) {
    	VPicNewsTemplate vPicNewsTemplate =	picMaterialService.getTemplateByid(id);
        request.setAttribute("template", vPicNewsTemplate);
        return new ModelAndView("weixin/material/text/picMaterialUpdate");

    }

    /**
     * 保存图文模板
     * 
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Json saveTemplate(VPicNewsTemplate vPicNewsTemplate) {
        return picMaterialService.saveTemplate(vPicNewsTemplate);
    }

    /**
     * 保存图文模板
     * 
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Json updateTemplate(VPicNewsTemplate vPicNewsTemplate) {
        Json json = new Json();
        try {
            picMaterialService.updateTemplate(vPicNewsTemplate);
        } catch (Exception e) {
            json.setMsg("更新失败");
            json.setSuccess(false);
        }

        json.setMsg("更新成功");
        json.setSuccess(true);
        return json;
    }

    /**
     * 删除图文模板
     * 
     * @return
     */
    @RequestMapping("/deleteMaterial")
    @ResponseBody
    public Json deleteMaterial(NewsTemplate newsTemplate) {
        Json json = new Json();
        try {
            baseService.delete(newsTemplate);
        } catch (Exception e) {
            json.setMsg("模板已经被使用，不要删除");
            json.setSuccess(false);
        }

        json.setMsg("删除成功");
        json.setSuccess(true);
        return json;
    }

    /**
     * 微信单图消息新增页面跳转
     * 
     * @return
     */
    @RequestMapping("/addArticle")
    public ModelAndView goAdd(NewsItem weixinArticle, HttpServletRequest req) {
        String templateId = req.getParameter("templateId");
        req.setAttribute("templateId", templateId);
        if (!StringUtils.isEmpty(weixinArticle.getId())) {
            weixinArticle = baseService.find(weixinArticle.getId(), NewsItem.class);
            req.setAttribute("weixinArticlePage", weixinArticle);
        }
        return new ModelAndView("weixin/material/text/weixinArticle-add");
    }

    /**
     * 保存文件信息
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> attributes = new HashMap<String, Object>();
        /** 页面控件的文件流 **/
        MultipartFile multipartFile = request.getFile("files[]");
        String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
        String documentTitle = oConvertUtils.getString(request.getParameter("documentTitle"));// 文件标题

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
        /** 构建图片保存的目录 **/
        String picPathDir = "/" + weixinPic + "/" + dateformat.format(new Date());
        // UUID
        String uuid = UUID.randomUUID().toString().replace("-", "");
        /** 得到图片保存目录的真实路径.tomcat下webapp路径 **/
        String picRealPathDir = this.getRootPath(request) + picPathDir + "/" + uuid + "/";
        /** 根据真实路径创建目录 **/
        File picSaveFile = new File(picRealPathDir);
        if (!picSaveFile.exists())
            picSaveFile.mkdirs();
        /** 页面控件的文件流 **/

        // 文件名称
        String picName = multipartFile.getOriginalFilename();

        WeixinPic pic = new WeixinPic();
        String picId = UUID.randomUUID().toString().replace("-", "");
        String picUrl = picPathDir + "/" + uuid + "/" + picName;
        pic.setPicId(picId);
        pic.setPicUrl(picUrl);
        baseService.save(pic);
        /** 拼成完整的文件保存路径加文件 **/
        String fileName = picRealPathDir + picName;
        File file = new File(fileName);
        String picDirPath = picPathDir + "/" + uuid + "/";

        try {
            multipartFile.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            j.setMsg("文件添加失败");
            j.setAttributes(attributes);
            return j;
        }

        attributes.put("url", picUrl);
        attributes.put("fileKey", picId);
        attributes.put("name", picName);
        attributes.put("viewhref", "viewPic?openViewFile&fileid=" + picId);
        attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + picId);
        j.setMsg("文件添加成功");
        j.setAttributes(attributes);

        return j;
    }

    /**
     * 附件预览页面打开链接
     * 
     * @return
     */
    @RequestMapping(params = "openViewFile", value = "/viewPic")
    public ModelAndView openViewFile(HttpServletRequest request) {
        String fileid = request.getParameter("fileid");
        WeixinPic weixinPic = baseService.find(fileid, WeixinPic.class);
        request.setAttribute("realpath", domain + weixinPic.getPicUrl());
        return new ModelAndView("weixin/material/text/imageView");
    }

    /**
     * 微信单图消息新增页面跳转
     * 
     * @return
     */
    /**
     * @param weixinArticle
     * @param templateId
     * @param req
     * @return
     */
    @RequestMapping("/saveArticle")
    @ResponseBody
    public Json saveArticle(NewsItem weixinArticle, Long templateId, HttpServletRequest req) {
        NewsTemplate newsTemplate = new NewsTemplate();
        newsTemplate.setId(templateId);
        weixinArticle.setNewsTemplate(newsTemplate);
        baseService.save(weixinArticle);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("保存成功");
        return json;
    }

    /**
     * 微信单图消息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goMessage")
    public ModelAndView goMessage(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        // request.setAttribute("templateId", templateId);
        if (!StringUtils.isEmpty(templateId)) {
            List<NewsItem> headerList =  picMaterialService.getArticles(Long.parseLong(templateId));
            if (headerList.size() > 0) {
            	Date temp = headerList.get(0).getCreateDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                request.setAttribute("addtime", sdf.format(temp));
                request.setAttribute("headerNews", headerList.get(0));
                if (headerList.size() > 1) {
                    List<NewsItem> list = new ArrayList<NewsItem>(headerList);
                    list.remove(0);
                    request.setAttribute("newsList", list);
                }
            }
        }
        return new ModelAndView("weixin/material/text/showmessage");
    }

    /**
     * 微信单图消息编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(NewsItem weixinArticle, HttpServletRequest req) {
        if (weixinArticle.getId() != null) {
            weixinArticle = baseService.find(weixinArticle.getId(), NewsItem.class);
            req.setAttribute("weixinArticle", weixinArticle);
        }
        return new ModelAndView("weixin/material/text/weixinArticle-update");
    }

    /**
     * 微信单图消息新增页面跳转
     * 
     * @return
     */
    /**
     * @param weixinArticle
     * @param req
     * @return
     */
    @RequestMapping("/updateArticle")
    @ResponseBody
    public Json updateArticle(NewsItem weixinArticle,Long parentId, HttpServletRequest req) {
        NewsTemplate newsTemplate = baseService.find(parentId, NewsTemplate.class);
        weixinArticle.setNewsTemplate(newsTemplate);
        baseService.update(weixinArticle);
        
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg("保存成功");
        return json;
    }

    /**
     * 删除微信单图消息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(NewsItem weixinArticle, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "微信单图消息删除成功";
        try {
            baseService.delete(weixinArticle);
        } catch (Exception e) {
            e.printStackTrace();
            message = "微信单图消息删除失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 获取服务器root路径
     * 
     * @param request
     * @return
     */
    private String getRootPath(HttpServletRequest request) {
        /** 得到图片保存目录的真实路径.tomcat下项目路径 **/
        String projectPath = request.getSession().getServletContext().getRealPath("");
        String rootPath = "";
        try {
            projectPath = projectPath.replace('\\', '/');
            rootPath = projectPath.substring(0, projectPath.lastIndexOf("/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootPath;
    }

    /**
     * 转向信息页面
     * @return
     */
    @RequestMapping("/goContent")
    public String goContent(HttpServletRequest request,Long id, Model model){
        System.out.println("PicMaterialController.goContent");
        System.out.println("id = " + id);
        VNewsItem vNewsItem = picMaterialService.getVNewsItem(id);
        vNewsItem.setContent(HtmlUtils.htmlUnescape(vNewsItem.getContent()));
        model.addAttribute("newsItem", vNewsItem);
        return "weixin/material/news/newsContent";
    }

	/**
	 * 改变启用状态
	 * @param 
	 * @return
	 */
	@RequestMapping("/changeStatus")
	@ResponseBody
	public Json delete(HttpServletRequest request,Long id) {
		Json j = new Json();
		try {
			picMaterialService.changeStatus(id);
			j.setMsg(ControllerMsg.CHANGE_MSG_SUCC);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(ControllerMsg.CHANGE_MSG_ERROR);
			e.printStackTrace();
		}
		return j;
	}
}
