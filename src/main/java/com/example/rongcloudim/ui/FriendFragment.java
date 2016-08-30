package com.example.rongcloudim.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rongcloudim.R;
import com.example.rongcloudim.ui.util.SPUtil;

import io.rong.imkit.RongIM;

/**
 * Created by hecun on 2016/7/13.
 */
public class FriendFragment extends Fragment{

    private static FriendFragment mInstance;
    private Button btnFriend;

    public static FriendFragment getInstance(){
        if(mInstance == null){
            mInstance = new FriendFragment();
        }
        return mInstance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.fragment_friend, null);
        btnFriend = (Button) view.findViewById(R.id.btn_frient);
        String userId = SPUtil.getUserId("userId");
        if("10086".equals(userId)){
            btnFriend.setText("好友孙悟天");
        }else{
            btnFriend.setText("好友特兰克斯");
        }
        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RongIM.getInstance()!=null){
                    String userId = SPUtil.getUserId("userId");
                    if("10086".equals(userId)){
                        userId = "10010";
                    }else{
                        userId = "10086";
                    }
                    //开启单聊界面
                    RongIM.getInstance().startPrivateChat(getActivity(),userId,"单聊");
                }
            }
        });
        return view;
    }
}
