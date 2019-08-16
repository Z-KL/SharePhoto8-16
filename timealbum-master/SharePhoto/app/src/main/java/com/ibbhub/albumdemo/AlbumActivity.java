package com.ibbhub.albumdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ibbhub.album.AlbumFragment;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initView();

        //状态栏中的文字颜色和图标颜色，需要android系统6.0以上，而且目前只有一种可以修改（一种是深色，一种是浅色即白色）
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            AlbumActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }
    private MenuItem chooseMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        chooseMenu = menu.findItem(R.id.action_choose);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_choose) {
            if (item.getTitle().equals("选择")) {
                albumFragment.enterChoose();
            } else {
                albumFragment.cancelChoose();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private AlbumFragment albumFragment;
    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        albumFragment = (AlbumFragment) getSupportFragmentManager().findFragmentByTag("album");
        if (albumFragment == null) {
            albumFragment = new MyAlbumFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flParent, albumFragment);
        ft.commit();
    }

    public void onChooseModeChange(boolean isChoose) {
        chooseMenu.setTitle(isChoose ? "取消" : "选择");
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AlbumActivity.class);
        context.startActivity(starter);
    }

}
