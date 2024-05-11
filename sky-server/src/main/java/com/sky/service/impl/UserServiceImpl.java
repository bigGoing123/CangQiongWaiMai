package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final String WX_URL="https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserLoginVO login(String code) {
        User user= selectUserByCode(code);
        //为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO=UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();

        return userLoginVO;
    }

    public User selectUserByCode(String code){
        //调用微信接口服务，获取当前微信用户的openid
        Map<String, String> params = new HashMap<>();
        params.put("appid",weChatProperties.getAppid());
        params.put("secret",weChatProperties.getSecret());
        params.put("js_code",code);
        params.put("grant_type","authorization_code");
        String jsonString = HttpClientUtil.doGet(WX_URL, params);

        JSONObject jsonObject = JSON.parseObject(jsonString);
        String openid = jsonObject.getString("openid");

        //判断openid是否为空，为null表示登录失败，抛出业务异常
        if(openid==null)
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);

        //p判断当前用户是否为新用户，如果是则自动完成注册
        User user = userMapper.selectByOpenid(openid);
        if(user==null) {
            user=User.builder()
                    .openid(openid)

                    .build();

            userMapper.insert(user);
        }
        //返回这个用户对象

        return user;
    }
}
