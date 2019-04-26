package cn.threegiants.sprusermanager.comm.util;

import cn.threegiants.sprusermanager.business.entity.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @description: jwt工具类
 * @author: he.l
 * @create: 2019-04-24 17:04
 **/
public class JWTUtil {


    //有效时间
    private static Integer OUTHOURS = 20;

    private static String  SECRET = "iplat4cupdate";

    /**
     * 生成token
     * @param userEntity
     * @return
     * @throws IllegalArgumentException
     * @throws UnsupportedEncodingException
     */
    public static String getToken(UserEntity userEntity){
        String token = null;
        try{
        Algorithm al = Algorithm.HMAC256(SECRET);
        Instant instant = LocalDateTime.now().plusMinutes(OUTHOURS).atZone(ZoneId.systemDefault()).toInstant();
        Date expire = Date.from(instant);
        token = JWT.create()
                .withIssuer("auth0")
                .withSubject("userInfo")
                .withClaim("user_id", userEntity.getId())
                .withClaim("user_name", userEntity.getUserName())
                .withExpiresAt(expire)
                .sign(al);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("user_id").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 1.解析Token，同时也能验证Token，当验证失败返回null
     * 2.验证Token 时效性
     * @param token
     * @return
     */
    public static boolean verify (String token, String user_id) {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("user_id", user_id)
                    .build();
            jwt = verifier.verify(token);
            if (jwt.getExpiresAt().before(new Date())) {
                return false;
            }
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Instant instant = LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant();
        Date expire = Date.from(instant);
        System.out.print(expire.toString());
    }
}
