package com.hdu.myship.blackstone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AutoLayoutActivity implements View.OnClickListener{
    private String RegisterURL="http://api.blackstone.ebirdnote.cn/v1/user/register";//注册账号请求，post请求夹带json数据
    private String GetCodeURL="http://api.blackstone.ebirdnote.cn/v1/user/verifyCode/mobile";//发送验证码请求，post请求夹带json数据
    private RequestQueue requestQueue;//请求列

    private JsonObjectRequest submitRequest;
    private JsonObjectRequest sendCodeRequest;

    private BootstrapEditText studentNumber;
    private BootstrapEditText name;
    private BootstrapEditText sex;
    private BootstrapEditText email;
    private BootstrapEditText password;
    private BootstrapEditText confirm;//确认密码
    private BootstrapEditText phoneNumber;
    private BootstrapEditText verificationCode;//验证码

    private BootstrapButton sendCode;//发送验证码
    private BootstrapButton submit;//注册账号按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        initView();
        initEvents();
    }

    private void initView() {
        studentNumber= (BootstrapEditText) findViewById(R.id.register_bootStrap_editText_student_number);
        name= (BootstrapEditText) findViewById(R.id.register_bootStrap_editText_name);
        sex= (BootstrapEditText) findViewById(R.id.register_bootStrap_editText_sex);
        email= (BootstrapEditText) findViewById(R.id.register_bootStrap_editText_email);
        password= (BootstrapEditText) findViewById(R.id.register_bootStrap_editText_password);
        confirm= (BootstrapEditText) findViewById(R.id.register_bootStrap_editText_confirm);
        phoneNumber= (BootstrapEditText) findViewById(R.id.register_bootStrap_editText_phone);
        verificationCode= (BootstrapEditText) findViewById(R.id.register_bootStrap_editText_verification_code);

        sendCode= (BootstrapButton) findViewById(R.id.register_bootStrap_button_send_code);
        submit= (BootstrapButton) findViewById(R.id.register_bootStrap_button_submit);
    }

    private void initEvents() {
        requestQueue= Volley.newRequestQueue(this);//创建请求队列

        /**
         * 给按钮设置监听事件
         */
        sendCode.setOnClickListener(this);
        submit.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.register_bootStrap_button_send_code:getCode();
                break;
            case R.id.register_bootStrap_button_submit:submit();
                break;
        }
    }

    public void getCode()
    {
        /**
         * 用map建立键值对关系，将数据存入map中
         */

        Map<String,String> sendCodeMap=new HashMap<>();


        //将手机号码存入map中

        sendCodeMap.put("number",phoneNumber.getText().toString());

        /**
         * 用map构建用post请求的json数据
         */

        JSONObject sendeCodeJson;


        sendeCodeJson=new JSONObject(sendCodeMap);//post发送验证码的json数据

        /**
         * 创建jsonObject请求
         */

        /**
         * 注册账号请求
         */


        /**
         * 发送验证码请求
         */
        sendCodeRequest=new JsonObjectRequest(Request.Method.POST, GetCodeURL, sendeCodeJson, new Response.Listener<JSONObject>() {//请求正常回调处理
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    int code=jsonObject.getInt("code");
                    if(code!=88)
                    {
                        String message=jsonObject.getString("message");
                        Toast.makeText(RegisterActivity.this,message , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {//请求异常回调
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RegisterActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(sendCodeRequest);
    }



    public void submit()
    {   Map<String,String> submitMap=new HashMap<>();


        //将注册信息存入map中

        submitMap.put("pwd",password.getText().toString());
        submitMap.put("verifyCode",verificationCode.getText().toString());
        submitMap.put("mobile",phoneNumber.getText().toString());
        submitMap.put("studentId",studentNumber.getText().toString());
        submitMap.put("name",name.getText().toString());
        submitMap.put("gender",sex.getText().toString());
        submitMap.put("mail",email.getText().toString());

        JSONObject submitJson;

        submitJson=new JSONObject(submitMap);//post注册的json数据

        submitRequest=new JsonObjectRequest(Request.Method.POST, RegisterURL, submitJson, new Response.Listener<JSONObject>() {//请求正常回调处理
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    int code=jsonObject.getInt("code");
                    if(code!=88)
                    {
                        String message=jsonObject.getString("message");
                        Toast.makeText(RegisterActivity.this,message, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //startActivity(new Intent(RegisterActivity.this,PersonCenterActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {//请求异常回调
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RegisterActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(submitRequest);
    }
}