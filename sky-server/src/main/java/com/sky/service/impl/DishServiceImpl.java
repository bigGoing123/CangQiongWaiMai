package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表里添加1条数据
        dishMapper.insert(dish);
        //为口味赋值菜品在数据库中的id,利用了主键返回
        Long id = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        //可能用户并没有提交口味数据
        if (flavors != null && !flavors.isEmpty()) {
            //向口味表里添加多条数据
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishFlavorMapper.saveBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<DishVO> dishVOs = dishMapper.pageQuery(dto);
        return new PageResult(dishVOs.getTotal(), dishVOs.getResult());
    }

    @Override
    @Transactional
    public Result delete(List<Long> ids) {
        for(Long id:ids){
            //查看当前要删除的dish的status如果是1的话则不能进行删除
            Dish dish = dishMapper.selectById(id);
            if(dish.getStatus()==1)
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
        //查询菜品对应的套餐
        List<Dish> dishes = setMealDishMapper.selectMealIdsByDishIds(ids);
        if(!dishes.isEmpty())
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);

        //删除口味表中的数据
        dishFlavorMapper.deleteBatchByDishIds(ids);
        //删除菜品表中的数据
        dishMapper.deleteBatch(ids);
        return null;
    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        //菜品修改
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.updateById(dish);
        Long id = dish.getId();
        //口味修改
        List<DishFlavor> flavors = dishDTO.getFlavors();

//        dishMapper.updateDishFlavors(flavors);

        //删除当前菜品对应的所有口味数据
        dishFlavorMapper.deleteBatchByDishIds(new ArrayList<>(Arrays.asList(dish.getId())));

        //添加当前提交过来的口味数据
        if(flavors != null && !flavors.isEmpty()){

            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishFlavorMapper.saveBatch(flavors);
        }


        log.info("修改成功");
    }

    @Override
    public DishVO selectById(Long id) {
        ArrayList<DishFlavor> flavors = dishMapper.selectDishWithFlavorById(id);

        Dish dish = dishMapper.selectById(id);
        DishVO dishVO = new DishVO();

        BeanUtils.copyProperties(dish, dishVO);
//        BeanUtils.copyProperties(flavors, dishVO);//这是不对的，

        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    @Transactional
    public void updateStatus(Integer status,Long id) {

        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishMapper.updateById(dish);
    }
}
