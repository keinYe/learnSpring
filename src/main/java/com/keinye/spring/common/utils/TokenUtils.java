package com.keinye.spring.common.utils;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import com.keinye.spring.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class TokenUtils implements Serializable {
	private static final long serialVersionUID = -8934365083040483966L;

	private static final Long EXPIRATION = 604800L;

	public String createToken(User user, PrivateKey privateKey) {
		try {
			Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
			String token = Jwts.builder()
					.setIssuer("SpringBoot")
					.setAudience(user.getName())
					.setExpiration(expirationDate)
					.setIssuedAt(new Date())
					.claim("", new Object())
					.signWith(privateKey)
					.compact();
			return String.format("Bearer %s", token);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User validationToken(String token, PublicKey publicKey) {
		try {
            // 解密 Token，获取 Claims 主体
            Claims claims = Jwts.parserBuilder()
                    // 设置公钥解密，以为私钥是保密的，因此 Token 只能是自己生成的，如此来验证 Token
                    .setSigningKey(publicKey)
                    .build().parseClaimsJws(token).getBody();
            assert claims != null;
            // 验证 Token 有没有过期 过期时间
            Date expiration = claims.getExpiration();
            // 判断是否过期 过期时间要在当前日期之后
            if (!expiration.after(new Date())) {
                return null;
            }
            User sysUser = new User();
            //User.setUsername(claims.getAudience());
            //User.setUserRole(claims.get("role").toString());
            return sysUser;
		} catch (Exception e) {
			return null;
		}
	}
	

}
