package cn.threegiants.sprusermanager.business.service.impl;

import cn.threegiants.sprusermanager.business.entity.UserEntity;
import cn.threegiants.sprusermanager.business.mapper.UserMapper;
import cn.threegiants.sprusermanager.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 用户管理service
 * @author: he.l
 * @create: 2019-04-24 16:58
 **/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userManager;

    @Override
    public UserEntity findUserById(String userId) {
        UserEntity userEntity = userManager.findUserById(userId);
        return userEntity;
    }

    @Override
    public UserEntity findByUsername(UserEntity user) {
        UserEntity userEntity = userManager.findByUsername(user);
        return userEntity;
    }
}
