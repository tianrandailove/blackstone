package com.hdu.myship.blackstone;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import JavaBean.APIManager;
import JsonUtil.JsonResolverSpeciesDetailed;
import JsonUtil.JsonResolverSpeciesDetailedSave;
import database.Amphibia;
import database.Bird;
import database.Insect;
import database.Record;
import database.Reptiles;
import database.Species;
import database.SpeciesClasses;

public class MainActivity extends AutoLayoutActivity implements View.OnClickListener{
    private String TAG="MainActivity";
    private int textColor=Color.argb(100,74,144,226);
    private String SpeciesDetailedUrl= APIManager.rootDoname+"v1/species/";
    private String getCategoryURL=APIManager.rootDoname+"v1/species/categories";
    private RequestQueue requestQueue;//请求队列

    private FragmentManager fragmentManager;//fragment 管理者
    private FragmentTransaction transaction;//开启一个事列

    private SpeciesFragment speciesFragment;//创建一个speciesFragment
    private GuideFragment guideFragment;//创建guideFragment
    private AddRecordFragment addRecordFragment;//创建addRecordFragment
    private PersonalCenterFragment personalCenterFragment;//创建personalCenterFragment
    private SettingFragment settingFragment;//创建settingFragment
    private LoginedFragment loginedFragment;//

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String isLoginedFile="isLogin";
    private Boolean isLogined;
    /**
     * 底部菜单选项
     */
    private LinearLayout tab_species;
    private LinearLayout tab_guide;
    private LinearLayout tab_add_record;
    private LinearLayout tab_personal_center;
    private LinearLayout tab_setting;

    private TextView top_title;//头部
    /**
     * 以下5个textview仅仅只是为展示，以后用界面替换掉
     */
    private TextView textView_species;
    private TextView textView_guide;
    private TextView textView_add_record;
    private TextView textView_personal_center;
    private TextView textView_setting;
    /**
     * 以下5个imagebutton是底部菜单栏的图片按钮
     */
    private ImageButton imageButton_species;
    private ImageButton imageButton_guide;
    private ImageButton imageButton_add_record;
    private ImageButton imageButton_personal_center;
    private ImageButton imageButton_setting;
    public  int test;

    public static List<List<Record>> records;

    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        initData();
        setContentView(R.layout.activity_main);
        initView();//初始化控件
        initEvents();//添加逻辑事件控制
    }

    private void initData() {
        sharedPreferences=getSharedPreferences(isLoginedFile,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        int size=(DataSupport.findAll(Bird.class)).size()+(DataSupport.findAll(Amphibia.class)).size()+(DataSupport.findAll(Insect.class)).size()+(DataSupport.findAll(Reptiles.class)).size();
        System.out.println("size"+size+":"+(DataSupport.findAll(Species.class)).size());
        if(size!=(DataSupport.findAll(Species.class)).size())
        {
            DataSupport.deleteAll(Bird.class);
            DataSupport.deleteAll(Amphibia.class);
            DataSupport.deleteAll(Reptiles.class);
            DataSupport.deleteAll(Insect.class);
            DataSupport.deleteAll(Record.class);
            requestQueue= Volley.newRequestQueue(this);//创建请求队列
            List<Species> speciesList=DataSupport.findAll(Species.class);
            for(Species s:speciesList)
            {
                JsonObjectRequest speciesDetailedRequest=new JsonObjectRequest(Request.Method.GET, SpeciesDetailedUrl + s.getSingal(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            int code=jsonObject.getInt("code");
                            if(code==88)
                            {
                                JsonResolverSpeciesDetailedSave speciesDetailed=new JsonResolverSpeciesDetailedSave(jsonObject);
                                speciesDetailed.ResolveSpeciesDetailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                requestQueue.add(speciesDetailedRequest);
            }

            for(Species species:DataSupport.findAll(Species.class))
            {
                Record record=new Record(species.getChineseName(),species.getSingal(),species.getSpeciesType());
                record.save();
                System.out.println(species.getSingal());
            }

        }
//        records=new ArrayList<>();
//        createBasicRecords();

        updateUserInformation(this);
    }

    public void updateUserInformation(Context context)
    {
         IsLoginUtil loginUtil=new IsLoginUtil(context);
        if(loginUtil.getLogined())
        {
            UserInformationUtil informationUtil=new UserInformationUtil(context);
            informationUtil.updateUserInfomation();
        }
    }

    private void initView() {
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();//开启一个事物

        tab_species= (LinearLayout) findViewById(R.id.tab_species);
        tab_guide= (LinearLayout) findViewById(R.id.tab_guide);
        tab_add_record= (LinearLayout) findViewById(R.id.tab_add_record);
        tab_personal_center= (LinearLayout) findViewById(R.id.tab_personal_cneter);
        tab_setting= (LinearLayout) findViewById(R.id.tab_setting);

        top_title= (TextView) findViewById(R.id.textView_top_title);

        textView_species= (TextView) findViewById(R.id.textView_species);
        textView_guide= (TextView) findViewById(R.id.textView_guide);
        textView_add_record= (TextView) findViewById(R.id.textView_add_record);
        textView_personal_center= (TextView) findViewById(R.id.textView_personal_center);
        textView_setting= (TextView) findViewById(R.id.textView_setting);

        imageButton_species= (ImageButton) findViewById(R.id.imageButton_species);
        imageButton_guide= (ImageButton) findViewById(R.id.imageButton_guide);
        imageButton_add_record= (ImageButton) findViewById(R.id.imageButton_add_record);
        imageButton_personal_center= (ImageButton) findViewById(R.id.imageButton_personal_center);
        imageButton_setting= (ImageButton) findViewById(R.id.imageButton_setting);

    }

    private void initEvents()
    {
        transaction.replace(R.id.frame_layout,new SpeciesFragment()).commit();
        imageButton_species.setImageResource(R.mipmap.species_pressed);
        tab_guide.setOnClickListener(this);
        tab_setting.setOnClickListener(this);
        tab_species.setOnClickListener(this);
        tab_personal_center.setOnClickListener(this);
        tab_add_record.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        transaction=fragmentManager.beginTransaction();
        resetColor();
        resetImage();
        switch (v.getId())
        {
            case R.id.tab_species://切换到物种界面
                if(speciesFragment==null)
                {
                    speciesFragment=new SpeciesFragment();
                    transaction.replace(R.id.frame_layout,speciesFragment);//用该fragment替换activity_main.xml的frame_layout来实现界面的切换
                    imageButton_species.setImageResource(R.mipmap.species_pressed);
                    textView_species.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                }else
                {
                    transaction.replace(R.id.frame_layout,speciesFragment);
                    imageButton_species.setImageResource(R.mipmap.species_pressed);
                    textView_species.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                }

                break;
            case R.id.tab_guide://切换到指南界面
                if(guideFragment==null)
                {
                    guideFragment=new GuideFragment();
                    transaction.replace(R.id.frame_layout,guideFragment);
                    imageButton_guide.setImageResource(R.mipmap.guide_pressed);
                    textView_guide.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                }else
                {
                    transaction.replace(R.id.frame_layout,guideFragment);
                    imageButton_guide.setImageResource(R.mipmap.guide_pressed);
                    textView_guide.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                }

                break;
            case R.id.tab_add_record://切换到添加记录界面
                if(addRecordFragment==null)
                {
                    addRecordFragment=new AddRecordFragment();
                    transaction.replace(R.id.frame_layout,addRecordFragment);
                    imageButton_add_record.setImageResource(R.mipmap.add_record_pressed);
                    textView_add_record.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                }else
                {
                    transaction.replace(R.id.frame_layout,addRecordFragment);
                    imageButton_add_record.setImageResource(R.mipmap.add_record_pressed);
                    textView_add_record.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                }

                break;
            case R.id.tab_personal_cneter://切换到个人中心界面(登录前和登录后的界面，这里进行判断一下)

                isLogined=sharedPreferences.getBoolean("islogined",false);//默认为未登录
                if(isLogined==false)
                {
                    if(personalCenterFragment==null)
                    {
                        personalCenterFragment=new PersonalCenterFragment();
                        transaction.replace(R.id.frame_layout,personalCenterFragment);
                        imageButton_personal_center.setImageResource(R.mipmap.person_center_pressed);
                        textView_personal_center.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                    }else
                    {
                        transaction.replace(R.id.frame_layout,personalCenterFragment);
                        imageButton_personal_center.setImageResource(R.mipmap.person_center_pressed);
                        textView_personal_center.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                    }
                }else if(isLogined==true)
                {
                    if(loginedFragment==null)
                    {
                        loginedFragment=new LoginedFragment();
                        transaction.replace(R.id.frame_layout,loginedFragment);
                        imageButton_personal_center.setImageResource(R.mipmap.person_center_pressed);
                        textView_personal_center.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                    }else
                    {
                        transaction.replace(R.id.frame_layout,loginedFragment);
                        imageButton_personal_center.setImageResource(R.mipmap.person_center_pressed);
                        textView_personal_center.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                    }
                }

                break;

            case R.id.tab_setting://切换到设置界面
                if(settingFragment==null)
                {
                    settingFragment=new SettingFragment();
                    transaction.replace(R.id.frame_layout,settingFragment);
                    imageButton_setting.setImageResource(R.mipmap.setting_pressed);
                    textView_setting.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                }else
                {
                    transaction.replace(R.id.frame_layout,settingFragment);
                    imageButton_setting.setImageResource(R.mipmap.setting_pressed);
                    textView_setting.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_pressed_color));
                }

                break;
        }
        transaction.commit();//提交，必须commit以下，否则切换不会执行
    }
     private void   resetImage()
        {
            imageButton_species.setImageResource(R.mipmap.species_normal);
            imageButton_guide.setImageResource(R.mipmap.guide_normal);
            imageButton_add_record.setImageResource(R.mipmap.add_record_normal);
            imageButton_personal_center.setImageResource(R.mipmap.person_center_normal);
            imageButton_setting.setImageResource(R.mipmap.setting_normal);
        }
    private void resetColor()//重置颜色
    {
        textView_guide.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_normal_color));
        textView_species.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_normal_color));
        textView_add_record.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_normal_color));
        textView_personal_center.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_normal_color));
        textView_setting.setTextColor(getResources().getColor(R.color.bottom_bar_textView_text_normal_color));
    }

    public void createBasicRecords() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                DataSupport.deleteAll(Record.class);
                List<Record>birdRecord = DataSupport.where("speciesType=?", "bird").find(Record.class);
                List<Record>amphibiaRecord = DataSupport.where("speciesType=?", "amphibia").find(Record.class);
                List<Record>reptilesRecord = DataSupport.where("speciesType=?", "reptiles").find(Record.class);
                List<Record>insectRecord = DataSupport.where("speciesType=?", "insect").find(Record.class);
                records.add(birdRecord);
                records.add(amphibiaRecord);
                records.add(reptilesRecord);
                records.add(insectRecord);
            }
        }).start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(),"再按一次退出程序" , Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        /**
         * 主要是实现各种fragment的切换
         * @param v
         */
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        moveTaskToBack(true);
    }
}
