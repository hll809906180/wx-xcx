package cn.threegiants.sprusermanager.comm.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 响应体
 * @author: he.l
 * @create: 2019-04-24 14:08
 **/
@Data
public class ResBody<T extends Object> implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3468560134846477959L;
    /**
     * 返回码
     */
    private String retCode;
    /**
     * 描述
     */
    private String retDesc;
    /**
     * 返回体
     */
    private T rspBody;

}
