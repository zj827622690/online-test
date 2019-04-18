package com.zj.onlinetest.component;


import com.zj.onlinetest.utils.JwtTokenUtil;
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
        String authToken = request.getParameter( "token" );

        String userRole = jwtTokenUtil.getUserRoleFromToken( authToken );
        if (!Objects.equals( role, userRole )) {
            return "false";
        }
        return jwtTokenUtil.getUsernameFromToken( authToken );
    }
}
