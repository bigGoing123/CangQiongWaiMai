package com.sky.mapper;


import com.sky.annotation.AutoFill;
import com.sky.entity.SysFile;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    @Insert("insert into sys_file (id, name, type, size, url) " +
            "values(#{id}, #{name}, #{type}, #{size}, #{url})")
    void upload(SysFile saveFile);
}
