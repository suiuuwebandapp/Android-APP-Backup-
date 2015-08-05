package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.chat.controller.HXSDKHelper;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 通用设置页面
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class NormalSettingActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @BindString(R.string.NormalSetting)
    String strTitle;

    /**
     * 设置声音布局
     */
    @Bind(R.id.rl_switch_sound)
    RelativeLayout switch_sound;

    /**
     * 设置震动布局
     */
    @Bind(R.id.rl_switch_vibrate)
    RelativeLayout switch_vibrate;

    @Bind(R.id.rl_switch_notification)
    RelativeLayout switch_notification;

    /**
     * 打开新消息通知imageView
     */
    @Bind(R.id.iv_switch_open_notification)
    ImageView open_notification;

    /**
     * 关闭新消息通知imageView
     */
    @Bind(R.id.iv_switch_close_notification)
    ImageView close_notification;

    /**
     * 打开声音提示imageView
     */
    @Bind(R.id.iv_switch_open_sound)
    ImageView open_sound;

    /**
     * 关闭声音提示imageView
     */
    @Bind(R.id.iv_switch_close_sound)
    ImageView close_sound;

    /**
     * 打开消息震动提示
     */
    @Bind(R.id.iv_switch_open_vibrate)
    ImageView open_vibrate;

    /**
     * 关闭消息震动提示
     */
    @Bind(R.id.iv_switch_close_vibrate)
    ImageView close_vibrate;

    @Bind(R.id.rl_switch_speaker)
    RelativeLayout switch_speaker;

    /**
     * 打开扬声器播放语音
     */
    @Bind(R.id.iv_switch_open_speaker)
    ImageView switch_open_speaker;

    /**
     * 关闭扬声器播放语音
     */
    @Bind(R.id.iv_switch_close_speaker)
    ImageView switch_close_speaker;

    /**
     * 声音和震动中间的那条线
     */
    @Bind(R.id.dividing_line1)
    TextView dividing_line1;

    @Bind(R.id.dividing_line2)
    TextView dividing_line2;

    @Bind(R.id.iv_top_back)
    ImageView back;

    @Bind(R.id.tv_top_center)
    TextView title;

    @Bind(R.id.tv_top_right_more)
    ImageView more;

    private EMChatOptions chatOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_setting);

        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            return;
        }

        initView();
    }

    private void initView() {
        more.setVisibility(View.GONE);

        title.setVisibility(View.VISIBLE);
        title.setText(strTitle);

        back.setOnClickListener(this);
        switch_notification.setOnClickListener(this);
        switch_sound.setOnClickListener(this);
        switch_vibrate.setOnClickListener(this);
        switch_speaker.setOnClickListener(this);

        chatOptions = EMChatManager.getInstance().getChatOptions();

        if (chatOptions.getNotificationEnable()) {
            open_notification.setVisibility(View.VISIBLE);
            close_notification.setVisibility(View.INVISIBLE);
        } else {
            open_notification.setVisibility(View.INVISIBLE);
            close_notification.setVisibility(View.VISIBLE);
        }

        if (chatOptions.getNoticedBySound()) {
            open_sound.setVisibility(View.VISIBLE);
            close_sound.setVisibility(View.INVISIBLE);
        } else {
            open_sound.setVisibility(View.INVISIBLE);
            close_sound.setVisibility(View.VISIBLE);
        }

        if (chatOptions.getNoticedByVibrate()) {
            open_vibrate.setVisibility(View.VISIBLE);
            close_vibrate.setVisibility(View.INVISIBLE);
        } else {
            open_vibrate.setVisibility(View.INVISIBLE);
            close_vibrate.setVisibility(View.VISIBLE);
        }

        if (chatOptions.getUseSpeaker()) {
            switch_open_speaker.setVisibility(View.VISIBLE);
            switch_close_speaker.setVisibility(View.INVISIBLE);
        } else {
            switch_open_speaker.setVisibility(View.INVISIBLE);
            switch_close_speaker.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rl_switch_notification:
                if (open_notification.getVisibility() == View.VISIBLE) {
                    open_notification.setVisibility(View.INVISIBLE);
                    close_notification.setVisibility(View.VISIBLE);

                    switch_sound.setVisibility(View.GONE);
                    switch_vibrate.setVisibility(View.GONE);
                    dividing_line1.setVisibility(View.GONE);
                    dividing_line2.setVisibility(View.GONE);

                    chatOptions.setNotificationEnable(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgNotification(false);
                } else {
                    open_notification.setVisibility(View.VISIBLE);
                    close_notification.setVisibility(View.INVISIBLE);

                    switch_sound.setVisibility(View.VISIBLE);
                    switch_vibrate.setVisibility(View.VISIBLE);

                    dividing_line1.setVisibility(View.VISIBLE);
                    dividing_line2.setVisibility(View.VISIBLE);

                    chatOptions.setNotificationEnable(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgNotification(true);
                }
                break;

            case R.id.rl_switch_sound:
                if (open_sound.getVisibility() == View.VISIBLE) {
                    open_sound.setVisibility(View.INVISIBLE);
                    close_sound.setVisibility(View.VISIBLE);

                    chatOptions.setNoticeBySound(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSound(false);
                } else {
                    open_sound.setVisibility(View.VISIBLE);
                    close_sound.setVisibility(View.INVISIBLE);

                    chatOptions.setNoticeBySound(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSound(true);
                }
                break;

            case R.id.rl_switch_vibrate:
                if (open_vibrate.getVisibility() == View.VISIBLE) {
                    open_vibrate.setVisibility(View.INVISIBLE);
                    close_vibrate.setVisibility(View.VISIBLE);

                    chatOptions.setNoticedByVibrate(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(false);
                } else {
                    open_vibrate.setVisibility(View.VISIBLE);
                    close_vibrate.setVisibility(View.INVISIBLE);

                    chatOptions.setNoticedByVibrate(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
                }
                break;

            case R.id.rl_switch_speaker:
                if (switch_open_speaker.getVisibility() == View.VISIBLE) {
                    switch_open_speaker.setVisibility(View.INVISIBLE);
                    switch_close_speaker.setVisibility(View.VISIBLE);

                    chatOptions.setUseSpeaker(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSpeaker(false);
                } else {
                    switch_open_speaker.setVisibility(View.VISIBLE);
                    switch_close_speaker.setVisibility(View.INVISIBLE);

                    chatOptions.setUseSpeaker(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
                }
                break;

            case R.id.iv_top_back:
                finish();
                break;

            default:
                break;
        }

    }

}