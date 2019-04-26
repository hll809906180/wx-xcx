package cn.threegiants.sprusermanager.comm.filter;

import cn.threegiants.sprusermanager.business.service.UserService;
import cn.threegiants.sprusermanager.comm.annotations.PassToken;
import cn.threegiants.sprusermanager.comm.annotations.UserLoginToken;
import cn.threegiants.sprusermanager.business.entity.UserEntity;
import cn.threegiants.sprusermanager.comm.util.JWTUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description: 校验token合法性拦截器
 * @author: he.l
 * @create: 2019-04-24 16:21
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        logger.info("接口鉴权开启-------------------------》");
        logger.info("token:"+token);
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 userEntity id
                String userId;
                try {
                    userId = JWTUtil.getUserId(token);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                UserEntity userEntity = userService.findUserById(userId);
                if (userEntity == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
               return JWTUtil.verify(token,userId);
            }
        }
        logger.info("接口鉴权校验通过------------------------------------》");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
