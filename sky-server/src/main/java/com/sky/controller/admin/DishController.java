package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    public Result saveWithFlavor(@RequestBody  DishDTO dishDTO) {

        log.info("dishDTO:{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param dto
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dto) {
        PageResult pageResult = dishService.pageQuery(dto);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     */
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        Result delete = dishService.delete(ids);
        return delete;
    }


}
