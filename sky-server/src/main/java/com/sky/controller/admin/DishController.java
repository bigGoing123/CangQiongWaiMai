package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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

    /**
     * 修改菜品信息
     */
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        return Result.success("菜品信息修改成功");
    }

    @GetMapping("/{id}")
    public Result<DishVO> list(@PathVariable Long id) {
        DishVO dishVO = dishService.selectById(id);
        return Result.success(dishVO);
    }

    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status,Long id) {

        dishService.updateStatus(status,id);
        return Result.success(MessageConstant.DISH_ON_SALE);
    }

}
