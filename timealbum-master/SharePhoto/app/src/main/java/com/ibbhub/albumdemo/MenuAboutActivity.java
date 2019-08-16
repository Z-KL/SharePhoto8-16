package com.ibbhub.albumdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;



public class MenuAboutActivity extends AppCompatActivity {
    private ImageView mIvAboutleft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_about);
        mIvAboutleft=findViewById(R.id.about_iv_left);
        setlisteners();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            MenuAboutActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }
    private void setlisteners(){
        OnClick onClick=new OnClick();
        mIvAboutleft.setOnClickListener(onClick);
    }
    public class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent =null;
            switch (view.getId()){
                case(R.id.about_iv_left):
                    intent=new Intent(MenuAboutActivity.this,MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
            }
            startActivity(intent);
        }
    }
}
