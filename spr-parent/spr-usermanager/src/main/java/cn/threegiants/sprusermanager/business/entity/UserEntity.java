package cn.threegiants.sprusermanager.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-24 16:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
    String id;
    String userName;
    String password;
}
