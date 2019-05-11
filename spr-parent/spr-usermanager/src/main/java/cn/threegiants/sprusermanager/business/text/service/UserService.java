package cn.threegiants.sprusermanager.business.text.service;

import cn.threegiants.sprusermanager.business.text.entity.UserEntity;

public interface UserService {
    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    UserEntity findUserById(String userId);
    /**
     * 查找用户是否存在
     * @param userEntity
     * @return
     */
    UserEntity findByUsername(UserEntity userEntity);
}
