package android.microanswer.healthy.viewbean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.InfoListItem;
import android.microanswer.healthy.exception.JavaBeanDataLoadException;
import android.microanswer.healthy.fragment.BannerItemFragment;
import android.microanswer.healthy.tools.BaseTools;
import android.microanswer.healthy.tools.InternetServiceTool;
import android.microanswer.healthy.tools.JavaBeanTools;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Micro on 2016/6/22.
 */

public class BannerViewHolder extends RecyclerView.ViewHolder implements Runnable, ViewPager.OnPageChangeListener {

    private static final int WHAT_PAGER_CHANGE = 3;
    private static boolean playing = false;//记录Viewpager是否在播放

    private FragmentManager fragmentManager;
    private Context context;
    private ViewPager viewPager;
    private TextView tv_tag;
    private RadioButton radioButton[];

    private BannerViewPagerAdapter bannerViewPagerAdapter;


    public BannerViewHolder(View itemView, Context context, FragmentManager fragmentManager) {
        super(itemView);
        this.context = context;
        this.fragmentManager = fragmentManager;
        viewPager = (ViewPager) itemView.findViewById(R.id.viewpager_healthy_banner_viewpager);
        viewPager.addOnPageChangeListener(this);
        radioButton = new RadioButton[4];
        radioButton[0] = (RadioButton) itemView.findViewById(R.id.viewpager_healthy_banner_radiobutton1);
        radioButton[1] = (RadioButton) itemView.findViewById(R.id.viewpager_healthy_banner_radiobutton2);
        radioButton[2] = (RadioButton) itemView.findViewById(R.id.viewpager_healthy_banner_radiobutton3);
        radioButton[3] = (RadioButton) itemView.findViewById(R.id.viewpager_healthy_banner_radiobutton4);
        tv_tag = (TextView) itemView.findViewById(R.id.viewpager_banner_tag);
    }

    private boolean isplaying = false;

    public void startplay() {
        if (isplaying) {
            return;
        }
        new Thread(this).start();
    }

    /**
     * 刷新banner内容
     */
    public void refresh() {
        //TODO Viewpager刷新内容
        updateData();
    }

    public void loadData() {
        //TODO 加载内容
        if (viewPager.getAdapter() == null) {
            updateData();
        }
    }


    /**
     * 加载网络内容
     */
    private void updateData() {
        if (loader == null) {
            loader = new DataLoader();
        }

        if (!isLoading) {
            new Thread(loader).start();
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private String[] tags = {"", "企业要闻", "医疗新闻", "生活贴士", "药品新闻", "食品新闻", "社会热点", "疾病快讯"};

    @Override
    public void onPageSelected(int position) {
        radioButton[position].setChecked(true);
        tv_tag.setText(tags[bannerViewPagerAdapter.getitemInfo(position).getInfoclass()]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void run() {
        isplaying = true;
        while (isplaying) {
            SystemClock.sleep(4000);
            Message msg = handler.obtainMessage();
            msg.what = WHAT_PAGER_CHANGE;
            msg.arg1 = (viewPager.getCurrentItem() == 3 ? 0 : viewPager.getCurrentItem() + 1);
            msg.sendToTarget();
        }
    }


    class BannerViewPagerAdapter extends FragmentPagerAdapter {
        private BannerItemFragment infoFragments[];

        public BannerViewPagerAdapter(FragmentManager fm, ArrayList<InfoListItem> data) {
            super(fm);
            infoFragments = new BannerItemFragment[4];
            for (int i = 0; i < 4; i++) {
                infoFragments[i] = new BannerItemFragment();
                infoFragments[i].setInfoListItem(data.get(i));
//                Log.i("BannerViewHolder",infoFragments[i].toString());
            }
        }

        public InfoListItem getitemInfo(int index) {
            return infoFragments[index].getInfoListItem();
    }

        @Override
        public Fragment getItem(int position) {
            return infoFragments[position];
        }


        @Override
        public int getCount() {
            return infoFragments.length;
        }
    }


    private boolean isLoading = false;
    private DataLoader loader;

    class DataLoader implements Runnable {

        @Override
        public void run() {
            isLoading = true;
            try {
                ArrayList<InfoListItem> data = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    data.addAll(JavaBeanTools.Info.getInfoListData(1, 1, (i + 1), null));
                }

                Message msg  = handler.obtainMessage();
                msg.what = WHAT_DATA_LOADOK;
                msg.obj = data;
                handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private static final int WHAT_DATA_LOADOK = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_DATA_LOADOK) {
                bannerViewPagerAdapter = new BannerViewPagerAdapter(fragmentManager, (ArrayList<InfoListItem>) msg.obj);
                viewPager.setAdapter(bannerViewPagerAdapter);
                radioButton[0].setChecked(true);
                startplay();
            } else if (msg.what == WHAT_PAGER_CHANGE) {
                viewPager.setCurrentItem(msg.arg1, true);
            }
        }
    };
}
