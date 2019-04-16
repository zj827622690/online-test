package com.zj.onlinetest.controller;

import com.zj.onlinetest.utils.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Auther: zj
 * @Date: 2019/4/16 22:45
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        //HttpServletResponse.SC_METHOD_NOT_ALLOWED 状态码

        String aaa =RandomUtils.number( 6 );
        return aaa;
    }

}
