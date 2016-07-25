package android.microanswer.healthy;

import android.microanswer.healthy.bean.Friend;
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


    private TextView headview_title;
    private TextView headview_sex;
    private TextView headview_age;
    private TextView headview_city;
    private CircleImageView headview_img;


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

        ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + friend.getAvatar(), headview_img);
        headview_title.setText(friend.getAccount() + "");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(friend.getBirth());
        int yearstart = calendar.get(Calendar.YEAR);
        headview_age.setText((year - yearstart) + "岁");
        headview_city.setText(friend.getProvince()+" "+friend.getCity() + "");
        headview_sex.setText(friend.getGender() == 1 ? "男" : friend.getGender() == 0 ? "女" : "保密");

        alertDialog(friend.getAccount(),friend.toString()).show();

    }


    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return true;
    }
}
