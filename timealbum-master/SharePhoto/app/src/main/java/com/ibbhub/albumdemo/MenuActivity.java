package com.ibbhub.albumdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;


public class MenuActivity extends AppCompatActivity {
    private ImageView mIvMenuLeft;
    private LinearLayout mLyMenuBar5;


    //qq授权
    private static final String TAG = "MenuActivity";
    private static final String APP_ID= "1105602574";


    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mIvMenuLeft=findViewById(R.id.menu_iv_left);
        mLyMenuBar5=findViewById(R.id.menu_ly_bar5);

        mTencent =Tencent.createInstance(APP_ID,MenuActivity.this.getApplicationContext());

        setlisteners();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            MenuActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //


    }
    private void setlisteners(){
        OnClick onClick=new OnClick();
        mIvMenuLeft.setOnClickListener(onClick);
        mLyMenuBar5.setOnClickListener(onClick);
    }
    public class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent =null;
            switch (view.getId()){
                case R.id.menu_iv_left:
                    intent=new Intent(MenuActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case R.id.menu_ly_bar5:
                    intent=new Intent(MenuActivity.this,MenuAboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
            }
            startActivity(intent);
        }
    }

    //
    //
    //
    public void buttonLogin(View view){
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(MenuActivity.this,"all", mIUiListener);
    }
    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener{

        @Override
        public void onComplete(Object o) {
            Toast.makeText(MenuActivity.this,"授权成功", Toast.LENGTH_LONG).show();
            Log.e(TAG,"response:"+o);
            JSONObject obj =(JSONObject) o;
            try{
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        //是一个json串response.tostring，直接使用gson解析就好
                        Log.e(TAG, "登录成功" + o.toString());
//                        登录成功后进行Gson解析即可获得你需要的QQ头像和昵称
//                         ContactsContract.CommonDataKinds.Nickname 昵称
//                        Figureurl_qq_1 //头像
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");
                    }
                });
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(MenuActivity.this,"授权失败", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(MenuActivity.this,"授权取消", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //
    //
    //


}
