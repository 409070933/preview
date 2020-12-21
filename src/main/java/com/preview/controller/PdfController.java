package com.preview.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.util.Map;
import java.util.UUID;

@RestController
public class PdfController {

    @RequestMapping(value = "pdf",method = RequestMethod.GET)
    public ModelAndView onlineView(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("/pdf/index");
        return modelAndView;
    }
    @RequestMapping(value = "svePdfLabel",method = RequestMethod.POST)
    @ResponseBody
    public void saveLabe(@RequestBody String json){
        System.out.println(json);

    }
    @GetMapping("/download")
    @ResponseBody
    public void download(HttpServletResponse response, @RequestParam String filePath){
        File file = new File(filePath);
        String fileName = UUID.randomUUID().toString()+".pdf";
        if (file.exists()) { //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            try {
                response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                os.close();
                fis.close();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
