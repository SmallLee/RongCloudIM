package com.example.rongcloudim.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rongcloudim.R;
import com.example.rongcloudim.ui.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends AppCompatActivity implements RongIM.UserInfoProvider {
    private static final String TAG = "MainActivity";
    private Button btnOne;
    private Button btnTwo;
    private List<Friend> userList;
    private String mUserid;
    private EditText etUserName;
    private EditText etPassWord;
    private CheckBox mCbRemember;
    private String mUserName;
    private String mPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initViewAndData();
        /**
         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
         *                         如果 App 提供的 UserInfoProvider
         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */
        RongIM.setUserInfoProvider(this,true);
    }

    public void initViewAndData(){
        userList = new ArrayList<>();
        btnOne = (Button) findViewById(R.id.btn_connect_server_one);
        btnTwo = (Button) findViewById(R.id.btn_connect_server_two);
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassWord = (EditText) findViewById(R.id.et_password);
        mCbRemember = (CheckBox) findViewById(R.id.cb_remember);
        userList.add(new Friend("10086","特兰克斯",Constant.imageUrl1));
        userList.add(new Friend("10010","孙悟天",Constant.imageUrl2));
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.btn_connect_server_one:
                verify(Constant.token1);
                break;
            case R.id.btn_connect_server_two:
                verify(Constant.token2);
                break;
//            case R.id.btn_auto_refresh_userinfo://刷新用户信息
//                RongIM.getInstance().refreshUserInfoCache(new UserInfo(mUserid.equals("10086")?"10086":"10010",
//                        mUserid.equals("10086")?"我曾经是特兰克斯":"我曾经是孙悟天",
//                        mUserid.equals("10086")?Uri.parse(Constant.imageUrl3):Uri.parse(Constant.imageUrl4)));
//                break;
//            case R.id.btn_load_conversation:
//                break;
//            case R.id.btn_start_conversation:
//                break;
        }
    }

    public void autoInit(){
        btnOne.setText("连接融云服务器(特兰克斯)");
        btnTwo.setText("连接融云服务器(孙悟天)");
        String userName = SPUtil.getString("userName");
        String passWord = SPUtil.getString("passWord");
        if(!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(passWord)){
            etUserName.setText(userName);
            etPassWord.setText(passWord);
        }else {
            etUserName.setText("");
            etPassWord.setText("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoInit();
    }

    /**
     * 验证用户名和密码的有效性
     */
    public void verify(String token){
        mUserName = etUserName.getText().toString();
        mPassWord = etPassWord.getText().toString();
        if(TextUtils.isEmpty(mUserName)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (TextUtils.isEmpty(mPassWord)) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (!mPassWord.equals("1234") || !mUserName.equals("1234")) {
                    Toast.makeText(this, "用户名或者密码不正确", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    connectServer(token);
                }
            }
        }
    }

    public void connectServer(String token){
        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {
            //利用token连服务器
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                }
                @Override
                public void onSuccess(String userid) {
                    //userid，是我们在申请token时填入的userid
                    mUserid = userid;
                    Log.d(TAG, "onSuccess: "+userid);
                    SPUtil.saveUserId("userId",userid);
                    if(userid.equals("10086")){
                        btnOne.setText("连接融云服务器成功(特兰克斯)");
                        Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                        btnTwo.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                        },1000);
                    }else{
                        btnTwo.setText("连接融云服务器成功(孙悟天)");
                        Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                        btnOne.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                        },1000);
                    }
                    if(mCbRemember.isChecked()){
                        SPUtil.putString("userName",mUserName);
                        SPUtil.putString("passWord",mPassWord);
                    }else{
                        SPUtil.putString("userName","");
                        SPUtil.putString("passWord","");
                    }
                    RongCloudEvent.getInstance().setConnectedListener();
                }
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                }
            });
        }
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i:userList){
            if(i.userid.equals(s)){
                //从缓存或者自己服务端获取到数据后返回给融云SDK
                return new UserInfo(i.userid,i.name, Uri.parse(i.imageUrl));
            }
        }
        return null;
    }
}
