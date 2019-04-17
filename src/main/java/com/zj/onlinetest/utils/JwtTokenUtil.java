package com.zj.onlinetest.utils;

import com.zj.onlinetest.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  Created: zj
 *  Description:
 *    jwt工具类，用来生成jwt的token和token的验证刷新之类的
 */


@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USERROLE = "role";

//    @Autowired
//    UserDataService userDataService;

    @Value("${jwt.secret}")
    private String secret; //密钥

    @Value("${jwt.expiration}")
    private Long expiration; //过期时间

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 获取token的过期时间
     * @param token
     * @return
     */

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 从token中获取用户权限
     * @param token
     * @return
     */
    public String getUserRoleFromToken(String token) {
        String role;
        try {
            final Claims claims = getClaimsFromToken(token);
            role = (String) claims.get(CLAIM_KEY_USERROLE);
        } catch (Exception e) {
            role = null;
        }
        return role;
    }

    /**
     * 从token中获取claims
     * @param token
     * @return
     */

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生存token的过期时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        Boolean result = expiration.before( new Date(  ) );
        return result;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 生成token
     * @param user
     * @return
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_USERROLE, user.getRole());
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith( SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * token 是否可刷新
     * @param token
     * @return
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isTokenExpired( token )&&!isCreatedBeforeLastPasswordReset(created, lastPasswordReset) ;
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证token
     * @param token
     * @param user
     * @return
     */
    public Boolean validateToken(String token, User user) {

        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        final String role = getUserRoleFromToken(token);
        Boolean result = (
                username.equals( user.getUsername() )
                    &&!isTokenExpired( token )
//                    &&!isCreatedBeforeLastPasswordReset(created, user.getCreateTime())
                        &&!isCreatedBeforeLastPasswordReset(created, new Date( user.getCreateTime() ))
                    &&role.equals( user.getRole() )
        );
//        if(result) {
//            userDataService.editUserData(user);//这边是 记录User登录数据（如果31天后自动删除）
//        }
        return result;
    }
}

