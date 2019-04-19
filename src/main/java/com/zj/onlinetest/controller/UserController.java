package com.zj.onlinetest.controller;

import com.zj.onlinetest.Repository.UserRepository;
import com.zj.onlinetest.component.UserRoleAuthentication;
import com.zj.onlinetest.domain.Question;
import com.zj.onlinetest.domain.User;
import com.zj.onlinetest.enums.CommonEnum;
import com.zj.onlinetest.enums.RoleEnum;
import com.zj.onlinetest.service.QuestService;
import com.zj.onlinetest.service.UserService;
import com.zj.onlinetest.utils.ResultVoUtil;
import com.zj.onlinetest.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: zj
 * @Date: 2019/4/18 10:06
 * @Description:
 */
@RestController
public class UserController
{

    @Autowired
    UserRoleAuthentication userRoleAuthentication;
    @Autowired
    UserService userService;
    @Autowired
    QuestService questService;

    /**
     * 笔试者 获取自己的题目
     * @param request
     * @return
     */
    @GetMapping("/getSelfQuestions")
    @CrossOrigin
    public ResultVo getSelfQuestions(HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_USER.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        User nowUser = userService.selectOneByUsername( nowName );
        if (nowUser.getLimitTimes()<1) {
            return ResultVoUtil.error( HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                    CommonEnum.USERHASWDTAKEEDTEST.getMessage());
        }

        ArrayList<String> arrayList = new ArrayList<>(  );
        String[] strs = nowUser.getQuestions().split( "#");
        for (String str: strs) {
            if(!Objects.equals( str, "" )) {
                arrayList.add( str );
            }
        }

        ArrayList<Question> questionArrayList = new ArrayList<>(  );
        for (String id:arrayList) {
            Question question = questService.selectOneById( id );
            questionArrayList.add( question );
        }

        return ResultVoUtil.success(  CommonEnum.GETSELFQUESTIONSSUCCESS.getMessage(),questionArrayList);
    }


}
