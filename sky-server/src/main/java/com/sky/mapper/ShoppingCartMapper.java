package com.sky.mapper;


import com.sky.annotation.AutoFill;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCart> list(ShoppingCart shoppingCart) ;

    void updateById(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time)" +
                "values(#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where user_id=#{userId}")
    void delete(Long UserId);
}
