package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
//    @Insert("insert into orders (number, status, user_id, address_book_id, order_time, checkout_time, pay_method, pay_status, " +
//            "amount, remark, phone, address, user_name, consignee, cancel_reason, rejection_reason, cancel_time, " +
//            "estimated_delivery_time, delivery_status, delivery_time, pack_amount, tableware_number, tableware_status)" +
//
//            "values (#{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, #{checkoutTime}, #{payMethod}, " +
//            "#{payStatus}, #{amount}, #{remark}, #{phone}, #{address}, #{userName}, #{consignee}, #{cancelReason}, " +
//            "#{rejectionReason}, #{cancelTime}, #{estimatedDeliveryTime}, #{deliveryStatus}, #{deliveryTime}, " +
//            "#{packAmount}, #{tablewareNumber}, #{tablewareStatus})")
    void insert(Orders order);


    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);
    @Select("select * from orders where user_id = #{userId}")
    List<Orders> getByUserId(Long userId);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);
    /**
     * 根据订单状态和下单时间查询订单
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status=#{status} and order_time<#{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    void updateTimeOutByIds(Integer status, String cancelReason, LocalDateTime cancelTime, List<Long> ids);

    void updateDeliveringByIds(Integer completed, List<Long> ids);

    

    Page<Orders> pageQueryFromUser(OrdersPageQueryDTO ordersPageQueryDTO);

    Page<Orders> pageQueryFromAdmin(OrdersPageQueryDTO ordersPageQueryDTO);
}
