package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    @Insert("insert into orders (number, status, user_id, address_book_id, order_time, checkout_time, pay_method, pay_status, " +
            "amount, remark, phone, address, user_name, consignee, cancel_reason, rejection_reason, cancel_time, " +
            "estimated_delivery_time, delivery_status, delivery_time, pack_amount, tableware_number, tableware_status)" +

            "values (#{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, #{checkoutTime}, #{payMethod}, " +
            "#{payStatus}, #{amount}, #{remark}, #{phone}, #{address}, #{userName}, #{consignee}, #{cancelReason}, " +
            "#{rejectionReason}, #{cancelTime}, #{estimatedDeliveryTime}, #{deliveryStatus}, #{deliveryTime}, " +
            "#{packAmount}, #{tablewareNumber}, #{tablewareStatus})")
    void insert(Orders order);
}
