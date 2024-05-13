package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Transactional
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入到购物车的商品是否已经存在于购物车中
        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId= BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.list(shoppingCart);
        //如果存在只需要num+1
        if(shoppingCarts!=null&&shoppingCarts.size()>0){
            //一般来说只有一个结果，所以直接取第一个
            ShoppingCart sc= shoppingCarts.get(0);
            sc.setNumber(sc.getNumber()+1);
            shoppingCartMapper.updateById(sc);
        }
        //如果不存在，则需要新增一条记录
        else {
            //判断本次加入的是菜品还是套餐
            if(shoppingCartDTO.getDishId()!=null){
                //是菜品
                Dish dish = dishMapper.getById(shoppingCartDTO.getDishId());
                shoppingCart.setDishId(dish.getId() );
                shoppingCart.setName(dish.getName());

                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
            }
            else {
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setSetmealId(setmeal.getId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
                //TODO 这里有个问题就是，用set方法就可以插入，但是用对象拷贝，最后没法插入数据库
//                BeanUtils.copyProperties(setmeal,shoppingCart);
            }

            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }
}
