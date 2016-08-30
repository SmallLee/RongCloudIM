package com.example.rongcloudim.ui;

import android.content.Context;

import com.example.rongcloudim.ui.provider.ContactsProvider;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2016/7/30.
 */
public class RongCloudEvent {
    private static RongCloudEvent sRongCloudEvent;
    private static Context mContext;
    public static void init(Context context){
        if(sRongCloudEvent == null){
            sRongCloudEvent = new RongCloudEvent(context);
        }
    }

    public static RongCloudEvent getInstance(){
        return sRongCloudEvent;
    }

    public RongCloudEvent(Context context){
        mContext = context;
        initListener();
    }

    public void initListener(){

    }

    public void setConnectedListener(){
        //图片
        InputProvider.ExtendProvider[] provider = {new ImageInputProvider(RongContext.getInstance()),
                new CameraInputProvider(RongContext.getInstance()),//相机
                new LocationInputProvider(RongContext.getInstance()),
                new ContactsProvider(RongContext.getInstance())
        };//位置
        RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE,provider);
    }

}
