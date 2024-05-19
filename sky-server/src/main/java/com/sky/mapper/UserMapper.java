package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.LocalDateTime2TurpleDTO;
import com.sky.entity.User;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid = #{openid}")
    User selectByOpenid(String openid);


    @AutoFill(value = OperationType.INSERT)
    void insert(User user);

    @Select("select * from user where id = #{userId}")
    User selectByUserId(Long userId);

    Integer countByMap(HashMap<String, Object> map);

    List<Integer> countByDateList(List<LocalDateTime2TurpleDTO> dateList);

    List<Integer> sumByDateList(List<LocalDateTime2TurpleDTO> dateList);
}
