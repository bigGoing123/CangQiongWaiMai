package com.sky.controller.user;


import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("UserOrderController")
@RequestMapping("/user/order")
@Api("用户订单相关接口")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        OrderSubmitVO orderSubmitVO=orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO );
    }
}
