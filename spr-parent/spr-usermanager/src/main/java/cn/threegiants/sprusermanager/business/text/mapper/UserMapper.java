package cn.threegiants.sprusermanager.business.text.mapper;

import cn.threegiants.sprusermanager.business.text.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserEntity findUserById(String userid);

    UserEntity findByUsername(UserEntity userEntity);
}
