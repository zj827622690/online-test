package com.zj.user.component;

import com.zj.user.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Auther: zj
 * @Date: 2018/12/3 11:27
 * @Description:
 */
@Component
public class UserRoleAuthentication {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public String getUsernameAndAutenticateUserRoleFromRequest(HttpServletRequest request,String role) {
        String tokenHeader="Authorization";
        String tokenHead="Bearer ";
        String authHeader = request.getHeader( tokenHeader);
        String authToken = authHeader.substring( tokenHead.length() ); // token is the part after "Bearer "

        String userRole = jwtTokenUtil.getUserRoleFromToken( authToken );
        if (!Objects.equals( role, userRole )) {
            return "false";
        }
        return jwtTokenUtil.getUsernameFromToken( authToken );
    }
}
