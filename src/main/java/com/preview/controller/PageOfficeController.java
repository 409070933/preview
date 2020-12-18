package com.preview.controller;

import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.Map;

@RestController
public class PageOfficeController {


    @Value("${posyspath}")
    private String poSysPath;

    @Value("${popassword}")
    private String poPassWord;

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public ModelAndView showIndex(){
        ModelAndView mv = new ModelAndView("Index");
        return mv;
    }
    @RequestMapping(value = "onlineView",method = RequestMethod.GET)
    public ModelAndView onlineView(HttpServletRequest request, Map<String,Object> map,@PathParam("filePath") String filePath){

        PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
        poCtrl.setServerPage("/poserver.zz");//设置服务页面
        poCtrl.addCustomToolButton("保存","Save",1);//添加自定义保存按钮
        poCtrl.addCustomToolButton("盖章","AddSeal",2);//添加自定义盖章按钮
        poCtrl.setSaveFilePage("/save");//设置处理文件保存的请求方法
        String suffix=filePath.substring(filePath.lastIndexOf('.'),filePath.length());
        ModelAndView modelAndView = null;
        if (suffix.equals(".doc")||suffix.equals(".docx")){
            poCtrl.webOpen("file://"+filePath,OpenModeType.docAdmin,"张三");
            modelAndView = new ModelAndView("Word");
        }else if (suffix.equals(".xlsx")||suffix.equals(".xls")){
            poCtrl.webOpen("file://" + filePath, OpenModeType.xlsNormalEdit, "张三");
            modelAndView = new ModelAndView("Excel");
        }
        map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
        return modelAndView;
    }
    @RequestMapping("/save")
    public void saveFile(HttpServletRequest request, HttpServletResponse response){
        FileSaver fs = new FileSaver(request, response);
        fs.saveToFile(poSysPath + fs.getFileName());
        fs.close();
    }
    /**
     * 添加PageOffice的服务器端授权程序Servlet（必须）
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        com.zhuozhengsoft.pageoffice.poserver.Server poserver = new com.zhuozhengsoft.pageoffice.poserver.Server();
        poserver.setSysPath(poSysPath);//设置PageOffice注册成功后,license.lic文件存放的目录
        ServletRegistrationBean srb = new ServletRegistrationBean(poserver);
        srb.addUrlMappings("/poserver.zz");
        srb.addUrlMappings("/posetup.exe");
        srb.addUrlMappings("/pageoffice.js");
        srb.addUrlMappings("/jquery.min.js");
        srb.addUrlMappings("/pobstyle.css");
        srb.addUrlMappings("/sealsetup.exe");
        return srb;//
    }

    /**
     * 添加印章管理程序Servlet（可选）
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean2() {
        com.zhuozhengsoft.pageoffice.poserver.AdminSeal adminSeal = new com.zhuozhengsoft.pageoffice.poserver.AdminSeal();
        adminSeal.setAdminPassword(poPassWord);//设置印章管理员admin的登录密码
        adminSeal.setSysPath(poSysPath);//设置印章数据库文件poseal.db存放的目录
        ServletRegistrationBean srb = new ServletRegistrationBean(adminSeal);
        srb.addUrlMappings("/adminseal.zz");
        srb.addUrlMappings("/sealimage.zz");
        srb.addUrlMappings("/loginseal.zz");
        return srb;//
    }
}
