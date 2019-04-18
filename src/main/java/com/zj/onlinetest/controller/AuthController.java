package com.zj.onlinetest.controller;

import com.zj.onlinetest.Repository.UserRepository;
import com.zj.onlinetest.domain.User;
import com.zj.onlinetest.enums.CommonEnum;
import com.zj.onlinetest.utils.JwtTokenUtil;
import com.zj.onlinetest.utils.ResultVoUtil;
import com.zj.onlinetest.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Auther: zj
 * @Date: 2019/4/17 16:49
 * @Description:
 */
@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @CrossOrigin()
    public ResultVo login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        User user = userRepository.findOneByUsername( username );
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!Objects.equals( user.getPassword(), md5Password )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.USERNAMEORPASSWORD_ERROR.getMessage());
        }

        //用jwt生成token
        String token = jwtTokenUtil.generateToken( user );
        return ResultVoUtil.success( CommonEnum.LOGINSUCCEES.getMessage(),token );
    }

}
