package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);
    PageResult pageQuery(DishPageQueryDTO dto);

    Result delete(List<Long> ids);

    void update(DishDTO dishDTO);

    DishVO selectById(Long id);

    void updateStatus(Integer status,Long id);
}
