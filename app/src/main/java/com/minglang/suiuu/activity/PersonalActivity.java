package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.SystemBarTintManager;
import com.minglang.suiuu.utils.Utils;

import java.io.File;

/**
 * 个人信息
 */
public class PersonalActivity extends Activity {

    /**
     * 返回
     */
    private ImageView back;

    /**
     * 保存
     */
    private TextView save;

    /**
     * 编辑头像
     */
    private CircleImageView headImage;

    /**
     * 编辑昵称
     */
    private EditText editNickName;

    /**
     * 编辑所在地
     */
    private EditText editLocation;

    /**
     * 编辑签名
     */
    private EditText editSignature;

    public PersonalActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        initView();


        ViewAction();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_NickName = editNickName.getText().toString();
                String str_Location = editLocation.getText().toString();
                String str_Signature = editSignature.getText().toString();

                Toast.makeText(PersonalActivity.this, "昵称:" + str_NickName + ",所在地:" + str_Location + ",签名:" + str_Signature, Toast.LENGTH_SHORT).show();
            }
        });

        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.selectPicture(PersonalActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(data==null){
            return;
        }

        switch (requestCode){

            case AppConstant.KITKAT_LESS:
                Uri uri =data.getData();
                Utils.cropPicture(PersonalActivity.this,uri);
                break;

            case AppConstant.KITKAT_ABOVE:
                Uri uri2 =data.getData();

                String imagePath = Utils.getPath(PersonalActivity.this,uri2);

                Utils.cropPicture(PersonalActivity.this,Uri.fromFile(new File(imagePath)));
                break;

            case AppConstant.INTENT_CROP:

                Bitmap bitmap = data.getParcelableExtra("data");
                headImage.setImageBitmap(bitmap);

                break;

        }

    }

    /**
     * 初始化方法
     */
    private void initView() {

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            RelativeLayout personalRootLayout = (RelativeLayout) findViewById(R.id.personalRootLayout);
            personalRootLayout.setPadding(0, statusHeight, 0, 0);
        }

        back = (ImageView) findViewById(R.id.personalBack);

        save = (TextView) findViewById(R.id.personalSave);

        headImage = (CircleImageView) findViewById(R.id.personalHeadImage);

        editNickName = (EditText) findViewById(R.id.personalNickName);
        editLocation = (EditText) findViewById(R.id.personalLocation);
        editSignature = (EditText) findViewById(R.id.personalSignature);
    }

}
