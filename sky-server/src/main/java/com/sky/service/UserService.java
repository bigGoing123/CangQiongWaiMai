package com.sky.service;

import com.sky.entity.User;
import com.sky.vo.UserLoginVO;

public interface UserService {

    /**
     * 登录
     * @param code 微信登录code
     * @return 用户信息
     */
    UserLoginVO login(String code);
}
