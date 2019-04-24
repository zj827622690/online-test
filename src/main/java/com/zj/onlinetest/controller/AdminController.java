package com.zj.onlinetest.controller;

import com.zj.onlinetest.component.UserRoleAuthentication;
import com.zj.onlinetest.domain.Question;
import com.zj.onlinetest.domain.User;
import com.zj.onlinetest.enums.CommonEnum;
import com.zj.onlinetest.enums.RoleEnum;
import com.zj.onlinetest.service.CommonService;
import com.zj.onlinetest.service.QuestService;
import com.zj.onlinetest.service.UserService;
import com.zj.onlinetest.utils.*;
import com.zj.onlinetest.vo.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: zj
 * @Date: 2019/4/17 17:30
 * @Description: 管理员相关业务
 */
@RestController
public class AdminController {

    @Autowired
    UserRoleAuthentication userRoleAuthentication;

    @Autowired
    QuestService questService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    CommonService commonService;

    @Value("${local.url}")
    private String url;



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

        ArrayList<String> arrayList = new ArrayList<>(  );

        for (String userId:userIds) {
            User user = userService.selectOneById( userId );

            //用jwt生成token
            String token = jwtTokenUtil.generateToken( user );
            String testUrl = url+"static/admin/html/userExamConfirm.html"+"?token="+token;
            arrayList.add(testUrl);

            userService.changeUser( user,questionIds.length, str,testUrl);
        }

        return ResultVoUtil.success( CommonEnum.PUBLISHWRITTENTESTSUCCESS.getMessage(),arrayList );
    }

    /**
     * 获取所有笔试者（分页）
     * @param request
     * @param pageIndex
     * @return
     */
    @GetMapping("/getUserList")
    @CrossOrigin
    public ResultVo getUserList(HttpServletRequest request,
                                @RequestParam("page") Integer pageIndex) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        List<User> lists=userService.selectAllUser();
        ArrayList<User> userArrayList = new ArrayList<>(  );
        for (User exc :lists) {
            if (!Objects.equals( exc.getRole(), RoleEnum.ROLE_ADMIN.getMessage() )) {
                userArrayList.add( exc );
            }
        }

        try{
            List list_new=ListPagingUtils.getPaging( userArrayList,pageIndex-1,5 );
            return ResultVoUtil.successPage( CommonEnum.GETUSEALLSUCCESS.getMessage(),userArrayList.size(),list_new);
        } catch (IndexOutOfBoundsException e) {
            return ResultVoUtil.successPage( CommonEnum.GETUSEALLSUCCESS.getMessage(),0,null);
        }



    }

    /**
     * 获取所有笔试者的数量
     * @param request
     * @return
     */
    @GetMapping("/getAllUserTotal")
    @CrossOrigin
    public ResultVo getAllUserTotal(HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        List<User> lists=userService.selectAllUser();
        ArrayList<User> userArrayList = new ArrayList<>(  );
        for (User exc :lists) {
            if (!Objects.equals( exc.getRole(), RoleEnum.ROLE_ADMIN.getMessage() )) {
                userArrayList.add( exc );
            }
        }
        return ResultVoUtil.success( CommonEnum.GETUSEALLTOTALSUCCESS.getMessage(),userArrayList.size());

    }

    /**
     * 获取所有笔试者（不分页）
     * @param request
     * @return
     */
    @GetMapping("/getAllTestUser")
    @CrossOrigin
    public ResultVo getAllTestUser(HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        List<User> lists=userService.selectAllUser();
        ArrayList<User> userArrayList = new ArrayList<>(  );
        for (User exc :lists) {
            if (!Objects.equals( exc.getRole(), RoleEnum.ROLE_ADMIN.getMessage())
                    &&Objects.equals( exc.getQuestions(),null)) {
                userArrayList.add( exc );
            }
        }

        return ResultVoUtil.success( CommonEnum.GETUSEALLSUCCESS.getMessage(),userArrayList);
    }

    /**
     * 获取所有笔试题
     * @return
     */
    @GetMapping("/getAllQuestions")
    @CrossOrigin
    public ResultVo getAllQuestions() {
        List<Question> questionList = questService.selectAll();
        return ResultVoUtil.success( CommonEnum.GETALLQUESTIONSUCCESS.getMessage(),questionList );
    }

    /**
     * 获取所有笔试题(分页)
     * @param pageIndex
     * @return
     */
    @GetMapping("/getAllQuestionsWithPage")
    @CrossOrigin
    public ResultVo getAllQuestionsWithPage(@RequestParam("page") Integer pageIndex) {
        List<Question> questionList = questService.selectAll( pageIndex-1 );

        List<Question> all = questService.selectAll();

        return ResultVoUtil.successPage( CommonEnum.GETALLQUESTIONSUCCESS.getMessage(),
                                         all.size(),questionList );
    }

    /**
     * 回放
     * @param userId
     * @param questionId
     * @param room
     * @param request
     * @return
     */
    @GetMapping("/playBack")
    @CrossOrigin
    public ResultVo playBack(@RequestParam("userId") String userId,
                             @RequestParam("questionId") String questionId,
                             @RequestParam("room") String room,
                             HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        commonService.playbackAnswer( userId,questionId,room);//异步

        System.out.println( "序结束" );

        return ResultVoUtil.success(CommonEnum.PLAYBACKTESTSUCCESS.getMessage(),null);
    }

    /**
     * 获取发布笔试后的 笔试者列表
     * @param request
     * @return
     */
    @GetMapping("/getUserListAfterPublishWrittenTest")
    @CrossOrigin

    public ResultVo getUserListAfterPublishWrittenTest(HttpServletRequest request,
                                                       @RequestParam("page") Integer pageIndex) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        List<User> lists=userService.selectAllUser();
        ArrayList<User> userArrayList = new ArrayList<>(  );
        for (User exc :lists) {
            if (!Objects.equals( exc.getTestUrl(),null)&&!Objects.equals( exc.getNumber(),null )) {
                userArrayList.add( exc );
            }
        }
        try{
            List list_new=ListPagingUtils.getPaging( userArrayList,pageIndex-1,5 );
            return ResultVoUtil.successPage( CommonEnum.GETALLUSERLISTAFTERPUBLISHEDTESTSUCCESS.getMessage()
                    ,userArrayList.size(),list_new);
        }catch (IndexOutOfBoundsException e) {
            return ResultVoUtil.successPage( CommonEnum.GETALLUSERLISTAFTERPUBLISHEDTESTSUCCESS.getMessage()
                    ,0,null);
        }

    }

    /**
     * 实时观看
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/getSelfQuestionsById")
    @CrossOrigin
    public ResultVo getSelfQuestions(@RequestParam("userId") String userId,
                                     HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        User nowUser = userService.selectOneById( userId );

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
     * 删除 指定笔试者
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/removeUser")
    public ResultVo removeUser(@RequestParam("userId") String userId,
                               HttpServletRequest request) {

        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        if (StringUtils.isBlank( userId )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PARAMERSERROR.getMessage());
        }

        User nowUser = userService.selectOneById( userId );

        if (nowUser.getQuestions()!=null) {
            return ResultVoUtil.error(  HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.USERHASTAKEDTEST.getMessage());
        }

        userService.deleteUser( nowUser );

        return ResultVoUtil.success(CommonEnum.DELETEUSERSUCCESS.getMessage(),null);
    }

    /**
     * 编辑 指定笔试题
     * @param questionId
     * @param request
     * @return
     */
    @GetMapping("/changeQuestion")
    public ResultVo changeQuestion(@RequestParam("questionId") String questionId,
                                   @RequestParam("content") String content,
                                   HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        if (StringUtils.isBlank( questionId )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PARAMERSERROR.getMessage());
        }

        Question question = questService.selectOneById( questionId );
        question.setSubject( content );
        questService.saveOrUpdate( question );

        return ResultVoUtil.success( CommonEnum.CHANGEQUESTIONSUCCESS.getMessage(),null );

    }

    /**
     * 删除 指定笔试题
     * @param questionId
     * @param request
     * @return
     */
    @GetMapping("/deleteQuestion")
    public ResultVo deleteQuestion(@RequestParam("questionId") String questionId,
                                   HttpServletRequest request) {
        String nowName = userRoleAuthentication.
                getUsernameAndAutenticateUserRoleFromRequest( request, RoleEnum.ROLE_ADMIN.getMessage());

        if (Objects.equals(nowName, "false" )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PERRMISSIONERROR.getMessage());
        }

        if (StringUtils.isBlank( questionId )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.PARAMERSERROR.getMessage());
        }
        ArrayList<String> arrayList = new ArrayList<>(  );
        List<User> users = userService.selectAllUser();
        for (User user:users) {
            String[] strs = user.getQuestions().split( "#");
            for (String str: strs) {
                if(!Objects.equals( str, "" )) {
                    arrayList.add( str );
                }
            }
        }

        if (arrayList.contains( questionId )) {
            return ResultVoUtil.error( HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    CommonEnum.THISQUESTIONHASPUBLISHED.getMessage());
        }

        Question question = questService.selectOneById( questionId );
        questService.deleteQuestion( question );
        return ResultVoUtil.success( CommonEnum.DELETEQUESTIONSUCCESS.getMessage(), null);
    }


}
