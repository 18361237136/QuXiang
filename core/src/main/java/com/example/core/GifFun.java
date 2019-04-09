package com.example.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.core.util.ShareUtil;


/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/8 下午2:24
 * Describe:全局的API接口
 */
public class GifFun {

    public static boolean isDebug=false;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static Handler handler;

    private static boolean isLogin;

    private static long userId;

    private static String token;

    private static int loginType=-1;

    public static String BASE_URL=isDebug?"http://192.168.31.177:3000":"http://api.quxianggif.com";

    public static final int GIF_MAX_SIZE=20*1024*1024;

    /**
     * 初始化接口。这里会进行应用程序的初始化操作，一定要在代码执行的最开始调用。
     * @param c Context参数，注意这里要传入的是Application的Context，千万不能传入Activity或者Service的Context。
     */
    public static void initialize(Context c){
        context=c;
        handler=new Handler(Looper.getMainLooper());
        refreshLoginState();
    }

    //获取全局context
    public static Context getContext(){
        return context;
    }

    //获取创建在主线程上的Handler对象。
    public static Handler getHandler(){
        return handler;
    }

    //判断用户是否已登录。
    public static boolean isLogin(){
        return isLogin;
    }

    //返回当前应用包名
    public static String getPackageName(){
        return context.getPackageName();
    }

    //注销登录
    public static void logout(){
        ShareUtil.clear(Const.Auth.USER_ID);
        ShareUtil.clear(Const.Auth.TOKEN);
        ShareUtil.clear(Const.Auth.LOGIN_TYPE);
        ShareUtil.clear(Const.User.AVATAR);
        ShareUtil.clear(Const.User.BG_IMAGE);
        ShareUtil.clear(Const.User.DESCRIPTION);
        ShareUtil.clear(Const.User.NICKNAME);
        ShareUtil.clear(Const.Feed.MAIN_PAGER_POSITION);
        GifFun.refreshLoginState();
    }

    /**
     * 获取当前登录用户的id。
     * @return 当前登录用户的id。
     */
    public static long getUserId() {
        return userId;
    }

    /**
     * 获取当前登录用户的token。
     * @return 当前登录用户的token。
     */
    public static String getToken() {
        return token;
    }

    /**
     * 获取当前登录用户的登录类型。
     * @return 当前登录用户登录类型。
     */
    public static int getLoginType() {
        return loginType;
    }

    /**
     * 刷新用户登录状态
     */
    public static void refreshLoginState(){
        long u= ShareUtil.read(Const.Auth.USER_ID,0L);
        String t=ShareUtil.read(Const.Auth.TOKEN,"");
        int lt=ShareUtil.read(Const.Auth.LOGIN_TYPE,-1);
        isLogin=u>0&&!TextUtils.isEmpty(t)&&lt>=0;
        if(isLogin){
            userId=u;
            token=t;
            loginType=lt;
        }
    }
}
