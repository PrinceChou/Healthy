package android.microanswer.healthy.fragment;

import android.microanswer.healthy.BaseActivity;
import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.CookClassify2;
import android.microanswer.healthy.database.DataManager;
import android.microanswer.healthy.tools.JavaBeanTools;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.List;

/**
 * 由 Micro 创建于 2016/6/30.
 */

public class LifeFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private View root = null;
    private DataManager dataManager;
    private TabLayout tabLayout;
    private RadioButton rb_cook;
    private RadioButton rb_food;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (dataManager == null) {
            dataManager = new DataManager(getActivity());
        }
//        int a = 100/0;
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_life, null);
            tabLayout = (TabLayout) root.findViewById(R.id.fragment_life_tablayout);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_TAB_INIT_OK;
                        msg.obj = getCookClassifys();
                        msg.sendToTarget();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message ms = handler.obtainMessage();
                        ms.what = WHAT_ERROR;
                        ms.obj = e;
                        ms.sendToTarget();
                    }
                }
            }).start();
            rb_cook = (RadioButton) root.findViewById(R.id.fragment_life_rb_cook);
            rb_cook.setOnCheckedChangeListener(this);
            rb_food = (RadioButton) root.findViewById(R.id.fragment_life_rb_food);
            rb_food.setOnCheckedChangeListener(this);
            rb_cook.setChecked(true);
        }
        return root;
    }

    /**
     * 初始化Tab标签
     *
     * @param c2
     */
    private void initTabs(CookClassify2 c2) {
        List<CookClassify2.Tngou> tngous = c2.getTngou();
        for (CookClassify2.Tngou tngou : tngous) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(tngou.getName());
            tabLayout.addTab(tab);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (compoundButton == rb_food) {//跳转到健食物

            } else {//跳转到健康菜谱

            }
        }
    }


    /**
     * 获取菜谱分类
     *
     * @return
     */
    private CookClassify2 getCookClassifys() {
        CookClassify2 cookClassifyData = JavaBeanTools.Cook.getCookClassifyData();
        return cookClassifyData;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_ERROR) {
                BaseActivity.errorDialog(msg.obj.toString(), getActivity());
                return;
            }

            if (msg.what == WHAT_TAB_INIT_OK) {
                CookClassify2 c2 = (CookClassify2) msg.obj;
                initTabs(c2);
            }
        }
    };

    private static final int WHAT_TAB_INIT_OK = 2;
    private static final int WHAT_ERROR = 1;
}
