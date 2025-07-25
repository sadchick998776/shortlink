package com.zyh.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyh.shortlink.admin.common.constant.RedisConstant;
import com.zyh.shortlink.admin.convention.exception.ClientException;
import com.zyh.shortlink.admin.convention.exception.ServiceException;
import com.zyh.shortlink.admin.dao.UserRegisterReqDto;
import com.zyh.shortlink.admin.dao.entity.User;
import com.zyh.shortlink.admin.service.UserService;
import com.zyh.shortlink.admin.dao.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.zyh.shortlink.admin.convention.errorcode.BaseErrorCode.*;

/**
* @author 31748
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-07-25 10:51:14
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

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
        if (!hasUsername(username)){
            throw new ClientException(USER_NAME_EXIST_ERROR);
        }
        // 判断手机号是否存在
        User phoneBean = this.lambdaQuery()
                .eq(User::getPhone, phone)
                .one();
        if (ObjectUtil.isNotNull(phoneBean)){
            throw new ClientException(USER_PHONE_EXIST_ERROR);
        }
        // 判断电子邮箱是否存在
        User mailBean = this.lambdaQuery()
                .eq(User::getMail, mail)
                .one();
        if (ObjectUtil.isNotNull(mailBean)){
            throw new ClientException(USER_MAIL_EXIST_ERROR);
        }


        RLock lock = redissonClient.getLock(RedisConstant.LOCK_USER_REGISTER_KEY + username);
        try {
            if (lock.tryLock()){
                User user = BeanUtil.copyProperties(requestParam, User.class);
                boolean insert = save(user);
                if (!insert){
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
}




