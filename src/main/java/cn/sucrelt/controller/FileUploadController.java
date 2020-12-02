package cn.sucrelt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author sucre
 */
@Controller
@RequestMapping(path = "/file")
public class FileUploadController {
    @RequestMapping(path = "/fileUpload")
    public String fileUpLoad(HttpServletRequest request, MultipartFile uploadFile) throws IOException {
        System.out.println("SpringMVC方式进行文件上传...");
        // 先获取到要上传的文件目录
        String path = request.getSession().getServletContext().getRealPath("/uploads");
        System.out.println(path);
        // 创建File对象，一会向该路径下上传文件
        File file = new File(path);
        // 判断路径是否存在，如果不存在，创建该路径
        if (!file.exists()) {
            file.mkdirs();
        }
        // 获取到上传文件的名称
        String filename = uploadFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        // 把文件的名称唯一化
        filename = uuid + "_" + filename;
        // 上传文件
        uploadFile.transferTo(new File(file, filename));
        return "success";
    }
}
