package com.limp.controller;

import com.limp.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @intro ：
 * @auth ： shinians
 * @time ： 2018/12/31 17:46
 * @website： www.shinians.com
 */
@Controller
public class UploadController {
    @Autowired
    UploadService uploadService;

    /**
     * 访问首页
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "upload";
    }

    /**
     * 上传操作
     * @param file
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        uploadService.storeFile(file);
        redirectAttributes.addFlashAttribute("message",
                "但文件：上传成功 " + file.getOriginalFilename() );
        return "redirect:/";
    }

    /**
     *  批量上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploads")
//    @ResponseBody
    public String bacthFileUpload(MultipartFile[] file,RedirectAttributes redirectAttribute) throws Exception {
        StringBuffer buffer = new StringBuffer();
        for (MultipartFile multipartFile : file) {
            uploadService.storeFile(multipartFile);
            buffer.append(multipartFile.getOriginalFilename());
            buffer.append(",");
        }
        String all = buffer.substring(0, buffer.length() - 1);
        redirectAttribute.addFlashAttribute("message",
                "多文件：上传成功 " + all );
        return "redirect:/";
    }

}
