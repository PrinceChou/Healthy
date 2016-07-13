package android.microanswer.healthy.fragment;

import android.microanswer.healthy.R;
import android.microanswer.healthy.database.DataManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

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
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_life, null);
            tabLayout = (TabLayout) root.findViewById(R.id.fragment_life_tablayout);
            rb_cook = (RadioButton) root.findViewById(R.id.fragment_life_rb_cook);
            rb_cook.setOnCheckedChangeListener(this);
            rb_food = (RadioButton) root.findViewById(R.id.fragment_life_rb_food);
            rb_food.setOnCheckedChangeListener(this);
            rb_cook.setChecked(true);
        }
        return root;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (compoundButton == rb_food) {//跳转到健食物

            } else {//跳转到健康菜谱

            }
        }
    }



}
