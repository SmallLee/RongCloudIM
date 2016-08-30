package com.example.rongcloudim.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rongcloudim.R;

import io.rong.imkit.RongIM;

/**
 *会话界面
 */

public class ConversationActivity extends FragmentActivity  {

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        //单聊是targetId就是userId
        String targetId = getIntent().getData().getQueryParameter("targetId");
        //需要设置用户者信息，才能获取到title
        String title = getIntent().getData().getQueryParameter("title");
        tvTitle.setText(title);
//        String userId = SPUtil.getUserId("userId");
//        if(targetId.equals("10086")){
//            tvTitle.setText("孙悟天");
//        }else{
//            tvTitle.setText("特兰克斯");
//        }
        //发送文本消息
//     RongIM.getInstance().sendMessage(Conversation.ConversationType.CUSTOMER_SERVICE, targetId.equals("10086") ? "10010":"10086",
//             TextMessage.obtain("你好吗"), "来消息了", "pushData", new RongIMClient.SendMessageCallback() {
//                 @Override
//                 public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
//
//                 }
//
//                 @Override
//                 public void onSuccess(Integer integer) {
//                     Toast.makeText(ConversationActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                 }
//             });
        RongIM.setLocationProvider(new RongIM.LocationProvider() {
            @Override
            public void onStartLocation(Context context, LocationCallback locationCallback) {
                Toast.makeText(context, "地图功能暂未开放", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
