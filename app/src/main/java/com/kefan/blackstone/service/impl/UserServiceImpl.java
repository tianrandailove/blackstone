package com.kefan.blackstone.service.impl;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kefan.blackstone.data.api.UserApi;
import com.kefan.blackstone.data.listener.BaseResponseListener;
import com.kefan.blackstone.data.req.LoginReq;
import com.kefan.blackstone.model.User;
import com.kefan.blackstone.service.UserService;
import com.kefan.blackstone.util.UserSharePreferenceUtil;
import com.kefan.blackstone.vo.TokenVO;
import com.kefan.blackstone.vo.UserVO;

/**
 * @note: user service 实现类
 * @author: 柯帆
 * @date: 2018/6/10 下午12:12
 */
public class UserServiceImpl extends BaseServiceImpl implements UserService{

    private UserApi userApi;

    public UserServiceImpl() {

        userApi = new UserApi(context);

    }

    @Override
    public void login(String username, String pwd, BaseResponseListener<TokenVO> listener, Response.ErrorListener errorListener) {

        //封装请求参数
        LoginReq loginReq=new LoginReq(username,pwd);

        //创建请求
        JsonObjectRequest jsonObjectRequest = userApi.login(loginReq,listener,errorListener);

        //检查请求
        if (checkRequest(context, jsonObjectRequest)) {

            //执行请求
            requestQueue.add(jsonObjectRequest);

        }
    }

    @Override
    public void saveUser(TokenVO tokenVO, String pwd) {

        UserSharePreferenceUtil.saveUser(context,tokenVO,pwd,true);
    }

    @Override
    public User getUser() {
        return UserSharePreferenceUtil.getUser(context);
    }

    @Override
    public void logout() {
        TokenVO tokenVO=new TokenVO();
        UserVO userVO=new UserVO();
        tokenVO.setUser(userVO);
        UserSharePreferenceUtil.saveUser(context,tokenVO,null,false);
    }

    @Override
    public String getToken() {
        return UserSharePreferenceUtil.getUser(context).getToken();
    }

    @Override
    public String icon() {
        return UserSharePreferenceUtil.getUser(context).getAvatar();
    }

    @Override
    public boolean isLogined() {
        return UserSharePreferenceUtil.getUser(context).isIslogined();
    }


}
