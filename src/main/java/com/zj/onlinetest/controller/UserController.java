package com.zj.onlinetest.controller;

import com.zj.onlinetest.component.UserRoleAuthentication;
import com.zj.onlinetest.domain.Question;
import com.zj.onlinetest.domain.User;
import com.zj.onlinetest.enums.CommonEnum;
import com.zj.onlinetest.enums.RoleEnum;
import com.zj.onlinetest.service.QuestService;
import com.zj.onlinetest.service.UserService;
import com.zj.onlinetest.utils.ResultVoUtil;
import com.zj.onlinetest.utils.TimeUtils;
import com.zj.onlinetest.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Auther: zj
 * @Date: 2019/4/18 10:06
 * @Description: 笔试者的相关操作
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

    /**
     * 笔试者 获取自身用户信息
     * @param request
     * @return
     */
    @GetMapping("/getSelfInfo")
    public ResultVo getSelfInfo(HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_USER.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }
        User user =userService.selectOneByUsername( nowName );
        return ResultVoUtil.success( CommonEnum.GETTESTUSERSELFINFOSUCCESS.getMessage(),user );
    }

    /**
     * 开始答题
     * @return
     */
    @GetMapping("/startTest")
    @CrossOrigin
    public ResultVo startTest(HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_USER.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        User user = userService.selectOneByUsername( nowName );
        if (user.getLimitTimes()<1) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.USERHASWDTAKEEDTEST.getMessage());
        }
        user.setStartTime( TimeUtils.getNow());
        userService.saveOrUpdate( user );
        return ResultVoUtil.success( CommonEnum.STARTTESTSUCCESS.getMessage(),null );
    }

    /**
     * 结束答题
     * @return
     */
    @GetMapping("/endTest")
    @CrossOrigin
    public ResultVo endTest(HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_USER.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }
        User user = userService.selectOneByUsername( nowName );
        user.setEndTime(TimeUtils.getNow());
        user.setLimitTimes( user.getLimitTimes()-1 );
        userService.saveOrUpdate( user );
        return ResultVoUtil.success( CommonEnum.ENDTESTSUCCESS.getMessage(),null );
    }



}
