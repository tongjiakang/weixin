package com.vanke.mhj.controller.common;

import com.vanke.mhj.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RequestMapping(value = "/common/download")
@Controller
public class DownLoadController extends BaseController {

    private static final String OWNER = "业主信息导入模板";
    private static final String HOUSE = "房屋信息导入模板";
    private static final String PROJECT = "项目信息导入模板";
    private static final String HOUSETYPE = "户型信息导入模板";
    private static final String MATERIAL = "物料信息导入模板";
    private static final String PRODUCT = "产品信息导入模板";
    private static final String CONTRACT = "合同模板";

    private static final String XLS = ".xls";
    private static final String DOC = ".doc";

    @RequestMapping(value = "/{module}")
    public void download(HttpServletResponse response, @PathVariable String module) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String filename = "";
        String fileType = "";
        if (module.equals("owner")) {
            filename = OWNER;
            fileType = XLS;
        } else if (module.equals("house")) {
            filename = HOUSE;
            fileType = XLS;
        } else if (module.equals("project")) {
            filename = PROJECT;
            fileType = XLS;
        } else if (module.equals("houseType")) {
            filename = HOUSETYPE;
            fileType = XLS;
        } else if (module.equals("material")) {
            filename = MATERIAL;
            fileType = XLS;
        } else if (module.equals("product")) {
            filename = PRODUCT;
            fileType = XLS;
        } else if (module.equals("contract")) {
            filename = CONTRACT;
            fileType = DOC;
        }
        try {
            filename = URLEncoder.encode(filename, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + filename + fileType);
        try {
            String path = URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource("").getPath(), "utf-8") + "download/" + module + fileType;
            InputStream inputStream = new FileInputStream(new File(path));

            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
