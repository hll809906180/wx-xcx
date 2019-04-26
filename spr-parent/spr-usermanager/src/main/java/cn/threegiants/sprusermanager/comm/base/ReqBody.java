package cn.threegiants.sprusermanager.comm.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 请求体
 * @author: he.l
 * @create: 2019-04-24 14:12
 **/
@Data
public class ReqBody<T extends Object> implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3468560134846477959L;

    private T reqBody;
}
