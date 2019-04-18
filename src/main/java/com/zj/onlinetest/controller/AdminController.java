package com.zj.onlinetest.controller;

import com.zj.onlinetest.component.UserRoleAuthentication;
import com.zj.onlinetest.domain.Question;
import com.zj.onlinetest.domain.User;
import com.zj.onlinetest.enums.CommonEnum;
import com.zj.onlinetest.enums.RoleEnum;
import com.zj.onlinetest.service.QuestService;
import com.zj.onlinetest.service.UserService;
import com.zj.onlinetest.utils.RandomUtils;
import com.zj.onlinetest.utils.ResultVoUtil;
import com.zj.onlinetest.utils.TimeUtils;
import com.zj.onlinetest.vo.ResultVo;;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Auther: zj
 * @Date: 2019/4/17 17:30
 * @Description:
 */
@RestController
public class AdminController {

    @Autowired
    UserRoleAuthentication userRoleAuthentication;

    @Autowired
    QuestService questService;

    @Autowired
    UserService userService;

    /**
     * 新增笔试用户
     * @param name
     * @param request
     * @return
     */
    @PostMapping("/addNewUser")
    @CrossOrigin
    public ResultVo addNewUser(@RequestParam("username") String name,
                               HttpServletRequest request) {

        String username = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals( username, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        if (StringUtils.isBlank( name )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PARAMERSERROR.getMessage());
        }

        User user =userService.addNewUser( RandomUtils.number( 9 ),name,TimeUtils.getNow() );

        return ResultVoUtil.success( CommonEnum.ADDNEWUSERSUCCESS.getMessage(),user);
    }

    /**
     * 增加题库
     * @param subject
     * @param request
     * @return
     */
    @PostMapping("/addNewQuestion")
    @CrossOrigin
    public ResultVo addNewQuestion(@RequestParam("subject") String subject,
                                   HttpServletRequest request) {

        String username = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals( username, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        if (StringUtils.isBlank( subject )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PARAMERSERROR.getMessage());
        }

        questService.add( RandomUtils.number( 9 ),subject, TimeUtils.getNow() );

        return ResultVoUtil.success( CommonEnum.ADDQUESTIONSUCCESS.getMessage(),null);
    }

    /**
     * 发布笔试
     * @param userIds
     * @param questionIds
     * @param request
     * @return
     */
    @PostMapping("/PublishWrittenTest")
    @CrossOrigin
    @Transactional
    public ResultVo PublishWrittenTest(@RequestParam("userIds") String[] userIds,
                                       @RequestParam("questionIds") String[] questionIds,
                                       HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        if (userIds.length==0||questionIds.length==0) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PARAMERSERROR.getMessage());
        }
        String str= "#";
        for (String questionId:questionIds) {
            str =str+questionId+"#";
        }

        for (String userId:userIds) {
            User user = userService.selectOneById( userId );
            userService.changeUser( user,questionIds.length, str);
        }

        //todo
        //链接
        return ResultVoUtil.success( CommonEnum.PUBLISHWRITTENTESTSUCCESS.getMessage(),null );
    }

}
