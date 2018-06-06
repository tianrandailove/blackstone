package util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import model.User;

/**
 * @note:
 * @author: 柯帆
 * @date: 2018/6/6 下午4:13
 */
public class UserSharePreferenceUtil {

    private static final String USER_INFO_FILE="UesrInformation";

    public static final String ID="id";
    public static final String MOBILE="mobile";
    public static final String STUDENT_ID="studentId";
    public static final String NAME="name";
    public static final String GENDER="gender";
    public static final String TOKEN="token";
    public static final String EXPIREAT="expireAt";
    public static final String PASSWORD="password";
    public static final String ISLOGINED="isLogined";
    public static final String AVATAR="avatar";

    public static void save(Context context, JSONObject jsonObject,String pwd) throws JSONException {

        SharedPreferences.Editor editor=context.getSharedPreferences(USER_INFO_FILE,Context.MODE_PRIVATE).edit();

        JSONObject data=jsonObject.getJSONObject("data");
        JSONObject user=data.getJSONObject("user");

        editor.putLong(ID,user.getLong(ID));
        editor.putString(MOBILE,user.getString(MOBILE));
        editor.putString(STUDENT_ID,user.getString(STUDENT_ID));
        editor.putString(NAME,user.getString(NAME));
        editor.putString(GENDER,user.getString(GENDER));
        editor.putString(TOKEN,data.getString(TOKEN));
        editor.putLong(EXPIREAT,data.getLong(EXPIREAT));
        editor.putString(PASSWORD,pwd);
        editor.putBoolean(ISLOGINED,true);
        //设置头像
        if(user.has(AVATAR))
        {
            editor.putString(AVATAR,user.getString(AVATAR));
        }
        else
        {
            editor.putString(AVATAR,null);
        }
        editor.apply();

    }

    private static User getUser(Context context) {

        SharedPreferences sharedPreferences=context.getSharedPreferences(USER_INFO_FILE,Context.MODE_PRIVATE);

        User user=new User();
        user.setId(sharedPreferences.getLong(ID,0));
        user.setMobile(sharedPreferences.getString(MOBILE,""));
        user.setStudentId(sharedPreferences.getString(STUDENT_ID,""));
        user.setName(sharedPreferences.getString(NAME,""));
        user.setGender(sharedPreferences.getString(GENDER,""));
        user.setToken(sharedPreferences.getString(TOKEN,""));
        user.setPassword(sharedPreferences.getString(PASSWORD,""));
        user.setExpireAt(sharedPreferences.getLong(EXPIREAT,0));
        user.setAvatar(sharedPreferences.getString(AVATAR,""));
        user.setIslogined(sharedPreferences.getBoolean(ISLOGINED,false));

        return user;
    }

}
