package com.zyh.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyh.shortlink.admin.common.constant.RedisConstant;
import com.zyh.shortlink.admin.convention.exception.ClientException;
import com.zyh.shortlink.admin.convention.exception.ServiceException;
import com.zyh.shortlink.admin.dto.req.*;
import com.zyh.shortlink.admin.dao.entity.User;
import com.zyh.shortlink.admin.dto.resp.UserLoginResp;
import com.zyh.shortlink.admin.service.UserService;
import com.zyh.shortlink.admin.dao.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.statement.select.KSQLWindow;
import org.redisson.api.JsonType;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

import static com.zyh.shortlink.admin.convention.errorcode.BaseErrorCode.*;

/**
 * @author 31748
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2025-07-25 10:51:14
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final StringRedisTemplate stringRedisTemplate;

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;

    @Override
    public boolean hasUsername(String username) {
//        User user = this.lambdaQuery()
//                .eq(User::getUsername, username)
//                .one();
        return !userRegisterCachePenetrationBloomFilter.contains(username);

    }

    @Override
    public void register(UserRegisterReqDto requestParam) {
        String username = requestParam.getUsername();
        String phone = requestParam.getPhone();
        String mail = requestParam.getMail();
        // 判断用户名是否存在
        if (!hasUsername(username)) {
            throw new ClientException(USER_NAME_EXIST_ERROR);
        }
        // 判断手机号是否存在
        User phoneBean = this.lambdaQuery()
                .eq(User::getPhone, phone)
                .one();
        if (ObjectUtil.isNotNull(phoneBean)) {
            throw new ClientException(USER_PHONE_EXIST_ERROR);
        }
        // 判断电子邮箱是否存在
        User mailBean = this.lambdaQuery()
                .eq(User::getMail, mail)
                .one();
        if (ObjectUtil.isNotNull(mailBean)) {
            throw new ClientException(USER_MAIL_EXIST_ERROR);
        }


        RLock lock = redissonClient.getLock(RedisConstant.LOCK_USER_REGISTER_KEY + username);
        try {
            if (lock.tryLock()) {
                User user = BeanUtil.copyProperties(requestParam, User.class);
                boolean insert = save(user);
                if (!insert) {
                    throw new ServiceException(USER_REGISTER_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(username);
                return;
            }
            throw new ClientException(USER_REGISTER_ERROR);
        } finally {
            lock.unlock();
        }


    }

    @Override
    public void updateUser(UpdateUserReq requestParam) {
        String username = requestParam.getUsername();
        String password = requestParam.getPassword();
        String realName = requestParam.getRealName();
        String phone = requestParam.getPhone();
        String mail = requestParam.getMail();


        // 验证当前用户是否为登陆用户

        Boolean success = stringRedisTemplate.hasKey(RedisConstant.USER_LOGIN_KEY + username);
        if (!success){
            throw new ClientException("当前用户未登录");
        }


        //没有查询到用户姓名
        if (hasUsername(username)) {
            throw new ClientException(USER_NAME_VERIFY_ERROR);
        }
        this.lambdaUpdate()
                .eq(ObjectUtil.isNotNull(username), User::getUsername, username)
                .set(ObjectUtil.isNotNull(password), User::getPassword, password)
                .set(ObjectUtil.isNotNull(realName), User::getRealName, realName)
                .set(ObjectUtil.isNotNull(phone), User::getRealName, phone)
                .set(ObjectUtil.isNotNull(mail), User::getMail, mail)
                .update();


    }

    @Override
    public UserLoginResp login(UserLoginReq requestParam) {
        String username = requestParam.getUsername();
        String password = requestParam.getPassword();
        if (ObjectUtil.isNull(username) || ObjectUtil.isNull(password)) {
            throw new ClientException("用户名或者密码为空");
        }
        // 判断用户名是否存在
        User user = this.lambdaQuery()
                .eq(User::getUsername, username)
                .one();
        if (ObjectUtil.isNull(user)) {
            throw new ClientException("用户名不存在");
        }
        // 判断密码是否正确
        if (!user.getPassword().equals(password)) {
            throw new ClientException("密码输入错误");
        }
        // 判断用户账号是否注销
        if (user.getDelFlag() == 1) {
            throw new ClientException("用户已注销");
        }


        //判断redis中是否存在Token ,实现单点登录既单个Token登录
        Boolean b = stringRedisTemplate.hasKey(RedisConstant.USER_LOGIN_KEY + username);
        if (b) {
            throw new ClientException("用户已登录");
        }
        //封装redis cache层 Token信息方面
        String uuid = UUID.randomUUID().toString();
        RedisTokenAndUserInfo redisTokenAndUserInfo = new RedisTokenAndUserInfo();
        redisTokenAndUserInfo.setUserInfo(BeanUtil.copyProperties(user, UserInfo.class));
        redisTokenAndUserInfo.setToken(uuid);
        String jsonString = JSON.toJSONString(redisTokenAndUserInfo);
        stringRedisTemplate.opsForValue().set(RedisConstant.USER_LOGIN_KEY + username, jsonString, 30, TimeUnit.MINUTES);
        UserLoginResp userLoginResp = new UserLoginResp();
        userLoginResp.setToken(uuid);
        return userLoginResp;
    }

    @Override
    public Boolean checklogin(String token, String username) {
        Boolean success = stringRedisTemplate.hasKey(RedisConstant.USER_LOGIN_KEY + username);
        if (!success) {
            return false;
        }
        String jsonString = stringRedisTemplate.opsForValue().get(RedisConstant.USER_LOGIN_KEY + username);
        RedisTokenAndUserInfo redisTokenAndUserInfo = JSON.parseObject(jsonString, RedisTokenAndUserInfo.class);
        String tokenRedis = redisTokenAndUserInfo.getToken();
        if (!token.equals(tokenRedis)) {
            return false;
        }
        return true;

    }

    @Override
    public void exit(String username) {
        Boolean success = stringRedisTemplate.hasKey(RedisConstant.USER_LOGIN_KEY + username);
        if (success) {
            stringRedisTemplate.delete(RedisConstant.USER_LOGIN_KEY + username);
        }
    }
}




