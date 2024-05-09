package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
    //文件磁盘路径


    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String url = fileService.upload(file);

        return Result.success(url);

    }
}
