package android.microanswer.healthy;

import android.graphics.Color;
import android.microanswer.healthy.bean.User;
import android.microanswer.healthy.view.ItemView;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.sql.Date;

/**
 * Created by Micro on 2016/6/12.
 */

public class UserCenterActivity extends BaseActivity {
    private ActionBar actionBar;

    private ItemView headview;
    private ItemView userlikeview;
    private ItemView userfriend;
    private ItemView usernicknameview;
    private ItemView sexview;
    private ItemView birthdayview;
    private ItemView telview;
    private ItemView qqview;
    private ItemView sinaview;
    private ItemView singlnatureview;
    private ItemView cityview;

    private CollapsingToolbarLayout collapsingToolbarLayout;


    private void initview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_usercenter_toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(User.getUser().getAccount());
        }
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_usercenter_collapsingToolBarLayout);
        try{collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);}catch(Exception e){e.printStackTrace();}//设置展开字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后字体颜色;
        headview = (ItemView) findViewById(R.id.activity_usercenter_itemview_head);
        userlikeview = (ItemView) findViewById(R.id.activity_usercenter_itemview_userlike);
        userfriend = (ItemView) findViewById(R.id.activity_usercenter_itemview_userfriend);
        usernicknameview = (ItemView) findViewById(R.id.activity_usercenter_itemview_usernickname);
        sexview = (ItemView) findViewById(R.id.activity_usercenter_itemview_usersex);
        birthdayview = (ItemView) findViewById(R.id.activity_usercenter_itemview_userbrithday);
        telview = (ItemView) findViewById(R.id.activity_usercenter_itemview_tel);
        qqview = (ItemView) findViewById(R.id.activity_usercenter_itemview_qq);
        sinaview = (ItemView) findViewById(R.id.activity_usercenter_itemview_sina);
        singlnatureview = (ItemView) findViewById(R.id.activity_usercenter_itemview_singnauter);
        cityview = (ItemView) findViewById(R.id.activity_usercenter_itemview_city);
    }


    private void initdata() {
        User user = User.getUser();
        //TODO 加载头像
        Log.i("UserCenterActivity", user + "" + usernicknameview);

        ((TextView) usernicknameview.findViewById(R.id.activity_usercenter_textview_nickname)).setText(user.getAccount());
        if (user.getGender() == 1) {
            ((RadioButton) sexview.findViewById(R.id.activity_usercenter_radiobutton_boy)).setChecked(true);
            ((RadioButton) sexview.findViewById(R.id.activity_usercenter_radiobutton_gril)).setChecked(false);
        } else {
            ((RadioButton) sexview.findViewById(R.id.activity_usercenter_radiobutton_gril)).setChecked(true);
            ((RadioButton) sexview.findViewById(R.id.activity_usercenter_radiobutton_boy)).setChecked(false);
        }
        if (user.getGender() == 0) {
            //性别保密
        }

        Date userBirth = user.getBirth();
        ((TextView) birthdayview.findViewById(R.id.activity_usercenter_TextView_birthday)).setText(userBirth == null ? "未设置" : userBirth.toLocaleString());
        ((TextView) telview.findViewById(R.id.activity_usercenter_TextView_tel)).setText(user.getPhone());
        ((TextView) qqview.findViewById(R.id.activity_usercenter_TextView_qq)).setText(user.getQq());
        ((TextView) sinaview.findViewById(R.id.activity_usercenter_TextView_sina)).setText(user.getWeibo());
        ((TextView) singlnatureview.findViewById(R.id.activity_usercenter_TextView_singnauter)).setText(user.getSignature());
        ((TextView) cityview.findViewById(R.id.activity_usercenter_TextView_city)).setText(user.getCity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        initview();
        initdata();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

    public void onuttonclick(View v) {
        if (v.getId() == R.id.activity_usercenter_button_exitlogin) {
            loginOut();
            onBackPressed();
        }
    }


    private void loginOut() {
        File f = getAppInternalWorkDir();
        File userfile = new File(f.getAbsolutePath() + "/" + LoginActivity.userObjectFileName);
        if (userfile.exists()) {
            userfile.delete();
        }
        User.setUser(null);
    }
}
