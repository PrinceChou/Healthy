package android.microanswer.healthy;

import android.graphics.Color;
import android.microanswer.healthy.application.Healthy;
import android.microanswer.healthy.bean.Collected;
import android.microanswer.healthy.bean.User;
import android.microanswer.healthy.view.ItemView;
import android.microanswer.healthy.view.ItemView2;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.sql.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 48fe7ed6e993241f335882ddlb27c0d7
 * Created by Micro on 2016/6/12.
 */

public class UserCenterActivity extends BaseActivity implements View.OnClickListener {
    private ActionBar actionBar;

    private ItemView2 headview;
    private ItemView2 userlikeview;
    private ItemView2 userfriend;
    private ItemView2 usernicknameview;
    private ItemView2 sexview;
    private ItemView2 birthdayview;
    private ItemView2 telview;
    private ItemView2 qqview;
    private ItemView2 sinaview;
    private ItemView2 singlnatureview;
    private ItemView2 cityview;

    private CircleImageView headdview;

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
        headview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_head);
        headdview = (CircleImageView) headview.findViewById(R.id.activity_usercenter_imageview_head);
        userlikeview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_userlike);
        userlikeview.setOnClickListener(this);
        userfriend = (ItemView2) findViewById(R.id.activity_usercenter_itemview_userfriend);
        userfriend.setOnClickListener(this);
        usernicknameview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_usernickname);
        sexview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_usersex);
        birthdayview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_userbrithday);
        telview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_tel);
        qqview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_qq);
        sinaview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_sina);
        singlnatureview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_singnauter);
        cityview = (ItemView2) findViewById(R.id.activity_usercenter_itemview_city);
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

        ImageLoader.getInstance().displayImage(Healthy.IMAGE_URL + user.getAvatar(), headdview);

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_usercenter_itemview_userlike) {
            jumpTo(CollectedActivity.class, true);
        } else if (view.getId() == R.id.activity_usercenter_itemview_userfriend) {
            jumpTo(FriendsActivity.class, true);
        }
    }
}
