package com.ibbhub.albumdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.ibbhub.album.AlbumFragment;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView mIvMainMenu;
    private Button mbtnAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvMainMenu=findViewById(R.id.main_iv_menu);
        mbtnAlbum =findViewById(R.id.btnAlbum);



        initVariable();
        findViewById(R.id.btnAlbum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlbumActivity.start(MainActivity.this);
            }
       });
        mIvMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });

        //导航栏为透明

        //状态栏中的文字颜色和图标颜色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }



    }






    private void initVariable() {
        requestPermission();
    }




    private void requestPermission() {
        //获取storage权限
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }

    //
    //
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
    private long exitTime = 0;




}
