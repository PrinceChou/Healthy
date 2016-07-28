package android.microanswer.healthy;

import android.microanswer.healthy.bean.Friend;
import android.microanswer.healthy.view.ItemView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 由 Micro 创建于 2016/7/25.
 */

public class FriendActivity extends BaseActivity {

    private Friend friend;

    private TextView signature;
    private TextView headview_title;
    private TextView headview_sex;
    private TextView headview_age;
    private TextView headview_city;
    private CircleImageView headview_img;

    private ItemView2 tel;
    private ItemView2 qq;
    private ItemView2 weibo;
    private ItemView2 integral;
    private ItemView2 doman;
    private ItemView2 id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        suitToolBar(R.id.activity_friend_toolbar);
        setToolBarBackEnable();
        friend = (Friend) getIntent().getSerializableExtra("data");


        headview_title = (TextView) findViewById(R.id.activity_friend_headview_title);
        headview_age = (TextView) findViewById(R.id.activity_friend_headview_age);
        headview_city = (TextView) findViewById(R.id.activity_friend_headview_city);
        headview_img = (CircleImageView) findViewById(R.id.activity_friend_headview_img);
        headview_sex = (TextView) findViewById(R.id.activity_friend_headview_sex);
        signature = (TextView) findViewById(R.id.activity_friend_signature);

        tel = (ItemView2) findViewById(R.id.activity_friend_tel);
        qq = (ItemView2) findViewById(R.id.activity_friend_qq);
        weibo = (ItemView2) findViewById(R.id.activity_friend_weibo);
        integral = (ItemView2) findViewById(R.id.activity_friend_integral);
        doman = (ItemView2) findViewById(R.id.activity_friend_home);
        id = (ItemView2) findViewById(R.id.activity_friend_id);
        signature.setText((friend.getSignature() + "").replace("null", "未设置"));

        tel.getContentView(TextView.class).setText((friend.getPhone() + "").replace("null", "未设置"));
        qq.getContentView(TextView.class).setText((friend.getQq() + "").replace("null", "未设置"));
        weibo.getContentView(TextView.class).setText((friend.getWeibo() + "").replace("null", "未设置"));
        integral.getContentView(TextView.class).setText((friend.getIntegral() + "").replace("null", "未设置"));
        doman.getContentView(TextView.class).setText((friend.getDomain() + "").replace("null", "未设置"));
        id.getContentView(TextView.class).setText((friend.getId() + "").replace("null", "未设置"));

        ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + friend.getAvatar(), headview_img);
        headview_title.setText((friend.getAccount() + "").replace("null", "无名氏"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(friend.getBirth());
        int yearstart = calendar.get(Calendar.YEAR);
        headview_age.setText((year - yearstart) + "岁");
        headview_city.setText(friend.getProvince()+" "+friend.getCity() + "");
        headview_sex.setText(friend.getGender() == 1 ? "男" : friend.getGender() == 0 ? "女" : "保密");

//        alertDialog(friend.getAccount(),friend.toString()).show();

    }


    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return true;
    }
}
