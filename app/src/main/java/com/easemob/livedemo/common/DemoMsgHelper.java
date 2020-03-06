package com.easemob.livedemo.common;

import android.text.TextUtils;

import com.easemob.chatroommessage.ChatRoomMsgHelper;
import com.easemob.chatroommessage.OnMsgCallBack;
import com.easemob.livedemo.DemoConstants;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCustomMessageBody;
import com.hyphenate.chat.EMMessage;

import java.util.HashMap;
import java.util.Map;

public class DemoMsgHelper {
    private static DemoMsgHelper instance;
    private DemoMsgHelper(){}
    private String chatroomId;

    public static DemoMsgHelper getInstance() {
        if(instance == null) {
            synchronized (DemoMsgHelper.class) {
                if(instance == null) {
                    instance = new DemoMsgHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 需要在直播页面开始的时候初始化，防止chatroomId为空或不正确
     * @param chatroomId
     */
    public void init(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    /**
     * 发送文本消息
     * @param content
     * @param isBarrageMsg
     * @param callBack
     */
    public void sendMsg(String content, boolean isBarrageMsg, OnMsgCallBack callBack) {
        if(isBarrageMsg) {
            sendBarrageMsg(content, callBack);
        }else {
            sendTxtMsg(content, callBack);
        }
    }

    /**
     * 发送文本消息
     * @param content
     * @param callBack
     */
    public void sendTxtMsg(String content, OnMsgCallBack callBack) {
        EMMessage message = EMMessage.createTxtSendMessage(content, chatroomId);
        message.setChatType(EMMessage.ChatType.ChatRoom);
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                callBack.onSuccess(message);
            }

            @Override
            public void onError(int i, String s) {
                callBack.onError(i, s);
            }

            @Override
            public void onProgress(int i, String s) {
                callBack.onProgress(i, s);
            }
        });
    }

    /**
     * 发送礼物消息
     * @param giftId
     * @param num
     * @param callBack
     */
    public void sendGiftMsg(String giftId, int num, OnMsgCallBack callBack) {
        ChatRoomMsgHelper.getInstance().sendGiftMsg(giftId, num, callBack);
    }

    /**
     * 发送点赞消息
     * @param num
     * @param callBack
     */
    public void sendLikeMsg(int num, OnMsgCallBack callBack) {
        ChatRoomMsgHelper.getInstance().sendLikeMsg(num, callBack);
    }

    /**
     * 发送弹幕消息
     * @param content
     * @param callBack
     */
    public void sendBarrageMsg(String content, OnMsgCallBack callBack) {
        ChatRoomMsgHelper.getInstance().sendBarrageMsg(content, callBack);
    }
}
