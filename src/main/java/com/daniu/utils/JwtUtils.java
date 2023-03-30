package com.daniu.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtils {
    //定义默认有效期为一个小时 单位：毫秒
    public static final Long JWT_Default_Expires = 60 * 60 * 1000L;

    //设置明文密钥(注意 不能含有非法字符)
    public static final String JWT_KEY = "123456";

    //签发者
    public static final String Issuer = "FangDaniu";

    /**
     * 创建一个token
     *
     * @param id
     * @param subject            可以理解为存放实际需要传递的数据
     * @param SettingExpiresTime
     * @return
     */
    public static String createToken(Object data, Long SettingExpiresTime) {
        //创建我们将要使用的JWT签名算法对（token）令牌进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //获取当前时间并转换为date对象
        long nowMillis = System.currentTimeMillis();

        //签发时间
        Date now = new Date(nowMillis);

        //SettingExpiresTime判断用户是否需要设置token有效时间
        if (SettingExpiresTime == null) {
            //如果为空，将默认有效期赋值给SettingExpiresTime
            SettingExpiresTime = JWT_Default_Expires;
        }

        //结束时间 = 当前时间 + 设定的有效期时间
        Long ExpiresTime = nowMillis + SettingExpiresTime;
        //转换为date数据类型
        Date date = new Date(ExpiresTime);

        JwtBuilder builder = Jwts.builder()
//                .setHeaderParam("typ", "JWT")    //一下两行就是Header
//                .setHeaderParam("alg", "HS256")
                .setId(UUID.randomUUID()+"")
                .setSubject(JSON.toJSONString(data)) //主题，可以是json数据
                .setIssuer(Issuer)   //签发者
                .setIssuedAt(now)   //签发时间
                .signWith(signatureAlgorithm, generalKey()) //使用ES256对称加密算法签名，第二个参数为加密后的明文密钥
                .setExpiration(date);   //设置过期时间

        //compact:规则构建JWT并将其序列化为紧凑的URL安全字符串
        return builder.compact();
    }

    /**
     * 加密明文密钥
     *
     * @return
     */
    public static SecretKey generalKey() {
        //调用base64中的getDecoder方法获取解码器，调用解码器中的decode方法将明文密钥进行编码
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        //AES：加密方式
        SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        //返回加密后的密钥
        return secretKey;
    }

    /**
     * 解析jwt
     *
     * @param jwt
     * @return
     */
    //此处当解析不出的时候会抛出异常
    public static Claims parseToken(String jwt) throws Exception {
        return Jwts.parser()    //获取jwts的解析器
                .setSigningKey(generalKey())    //设置加密后的密钥进行比对
                .parseClaimsJws(jwt)    //解析传入的jwt字符串
                .getBody();     // 拿到claims对象返回
    }

    /**
     *
     * @param jwt
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> T parseToken(String jwt, Class<T> clazz) {
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(generalKey());
            Jws<Claims> claimsJws = parser.parseClaimsJws(jwt);
            Claims claims = claimsJws.getBody();
            String payload = claims.get("sub", String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payload, clazz);
        } catch (Exception e) {
            return null;
        }
    }

}