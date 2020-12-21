package com.preview.controller;

import com.preview.utils.HttpClientUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
public class PdfController {

    /**
     * 保存业务系统标注
     * @param json
     */
    @RequestMapping(value = "svePdfLabel",method = RequestMethod.POST)
    @ResponseBody
    public void saveLabe(@RequestBody String json){
        //回调业务系统pdf标注保存接口
//        HttpClientUtils.doPost("业务系统url",json);
        System.out.println(json);

    }
    /**
     * 查询业务系统标注
     * @return
     */
    @RequestMapping(value = "pdfLabelList",method = RequestMethod.GET)
    @ResponseBody
    public String pdfLabelList(){
        //回调业务系统pdf标注保存接口
//        String json =  HttpClientUtils.doGet("业务系统url").toJSONString();
        String jsonString ="[{\"content\":{\"text\":\"直至执\"},\"position\":{\"boundingRect\":{\"x1\":464.333251953125,\"y1\":1076.515625,\"x2\":521.53759765625,\"y2\":1102.515625,\"width\":991.6666666666666,\"height\":1403.3333333333333},\"rects\":[{\"x1\":464.333251953125,\"y1\":1076.515625,\"x2\":521.53759765625,\"y2\":1102.515625,\"width\":991.6666666666666,\"height\":1403.3333333333333}],\"pageNumber\":3},\"comment\":{\"text\":\"asdas \",\"emoji\":\"\"},\"id\":\"12899956780245203\"}]";
        return jsonString;
    }

    /**
     * 下载pdf
     * @param response
     * @param filePath
     */
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
