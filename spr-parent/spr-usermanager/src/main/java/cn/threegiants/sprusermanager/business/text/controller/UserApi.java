package cn.threegiants.sprusermanager.business.text.controller;

import cn.threegiants.sprusermanager.business.text.service.UserService;
import cn.threegiants.sprusermanager.comm.annotations.UserLoginToken;
import cn.threegiants.sprusermanager.business.text.entity.UserEntity;
import cn.threegiants.sprusermanager.comm.util.HttpUrlConnection;
import cn.threegiants.sprusermanager.comm.util.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-24 17:02
 **/
@RestController
@RequestMapping("api")
public class UserApi {

    private static Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    //登录
    @ApiOperation(value = "用户登录")
    @ApiParam(name = "userEntity", value = "三期挡板支付网关请求体", required = true)
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没填好"),@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对", response = String.class)})
    @RequestMapping(value="/login", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object login(@RequestBody UserEntity userEntity){
        JSONObject jsonObject=new JSONObject();
        UserEntity userEntityForBase =userService.findByUsername(userEntity);
        redisTemplate.opsForValue().set("users6", userEntityForBase);
        if(userEntityForBase ==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userEntityForBase.getPassword().equals(userEntity.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String token = JWTUtil.getToken(userEntityForBase);
                jsonObject.put("token", token);
                jsonObject.put("userEntity", userEntityForBase);
                return jsonObject;
            }
        }
    }

    @GetMapping("/getMessage")
    @ApiOperation(value = "获取信息")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数没填好"),@ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对", response = String.class)})
    @RequestMapping(value="/getMessage")
    @UserLoginToken
    public String getMessage(@RequestHeader String token) {
        UserEntity userEntity = (UserEntity) redisTemplate.opsForValue().get("users6");
        logger.info("缓存中的实体:" + JSONObject.toJSONString(userEntity));
        return "你已通过验证";
    }

    @RequestMapping("/wx")
    public String  getWxCheck(String jsCode){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxb5722d7befb3f5d2&secret=7f858a3a71e6827a5c4bc293b8fba754&js_code="
                +jsCode+ "&grant_type=authorization_code";
        String session_key = "";
        String openid = "";
        try{
            String res[] = HttpUrlConnection.requestJson(url);
            JSONObject oppidObj = JSONObject.parseObject(res[0]);
            openid = (String) oppidObj.get("openid");
            session_key = (String) oppidObj.get("session_key");
            System.out.println(res[0]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return session_key;
    }
}
