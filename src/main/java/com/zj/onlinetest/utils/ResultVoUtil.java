package com.zj.onlinetest.utils;


import com.zj.onlinetest.vo.ResultVo;

/**
 * @Auther: zj
 * @Date: 2019/4/17 10:51
 * @Description:
 */
public class ResultVoUtil {

    public static ResultVo<Object> success(String strMsg,Object object) {
        ResultVo<Object> resultVo=new ResultVo<>();
        resultVo.setCode( 200 );
        resultVo.setMsg( strMsg);
        resultVo.setData( object );
        return resultVo;
    }

    public static ResultVo<Object> error(Integer code, String str) {
        ResultVo<Object> resultVo=new ResultVo<>();
        resultVo.setCode( code);
        resultVo.setMsg( str);
        return resultVo;
    }


    public static ResultVo<Object> successPage(String strMsg,Integer size,Object object) {
        ResultVo<Object> resultVo=new ResultVo<>();
        resultVo.setCode( 0 );
        resultVo.setMsg( "");
        resultVo.setCount( size );
        resultVo.setData( object );
        return resultVo;
    }



}
