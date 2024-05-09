package com.sky.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.sky.entity.SysFile;
import com.sky.mapper.FileMapper;
import com.sky.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private FileMapper fileMapper;
    @Override
    public String upload(MultipartFile file) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件的类型
        String type = FileUtil.extName(originalFilename);
        //获取文件大小
        long size = file.getSize();

        //文件存储的磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断文件目录是否存在
        if(!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        //定义一个文件唯一标识码（UUID）
        String uuid = UUID.randomUUID().toString();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID);

        //将临时文件转存到指定磁盘位置
        file.transferTo(uploadFile);

        //设置下载的文件路径
        String url = fileUploadPath + fileUUID;

        //将文件信息保存到数据库
        SysFile saveFile = new SysFile();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);//转成kb
        saveFile.setUrl(url);
        fileMapper.upload(saveFile);
        return url;
    }
}
