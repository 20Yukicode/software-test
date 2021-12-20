package com.soa.springcloud.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JWTUtils {

    static String SECRETKEY = "KJHUhjjJYgYUllVbXhKDHXhkSyHjlNiVkYzWTBac1Yxkjhuad";

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey(String stringKey) {
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * @param id 唯一id，uuid即可
     * @param subject json形式字符串或字符串，增加用户非敏感信息存储，如用户id或用户账号，与token解析后进行对比，防止乱用
     * @param expirationDate  生成jwt的有效期，单位秒
     * @return jwt token
     * @throws Exception
     */
    public static String createJWT(String id, String subject, long expirationDate) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey(SECRETKEY);
        JwtBuilder builder = Jwts.builder().setIssuer("").setId(id).setIssuedAt(now).setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (expirationDate >= 0) {
            long expMillis = nowMillis + expirationDate*1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt，获取实体
     * @param jwt
     */
    public static Claims parseJWT(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        SecretKey key = generalKey(SECRETKEY);
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 解密，获取uid
     */
    public static int getId(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        SecretKey key = generalKey(SECRETKEY);
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        JSONObject tem = JSONObject.parseObject(claims.getSubject());
        int id = tem.getInteger("id");
        return id;
    }
    /**
     * 实例演示
     */
    public static void main(String[] args) {
        try {
            JSONObject subject = new JSONObject(true);
            subject.put("tem", "哈哈哈");
            subject.put("userName", "sixmonth");
            String token = createJWT("1", subject.toJSONString(), 10);//10秒过期
            Claims claims = parseJWT(token);
            System.out.println("解析jwt："+claims.getSubject());
            JSONObject tem = JSONObject.parseObject(claims.getSubject());
            System.out.println("获取json对象内容："+tem.getString("userName"));
            System.out.println(claims.getExpiration()+"///"+claims.getExpiration().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
