package android.microanswer.healthy.fragment;

import android.microanswer.healthy.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private RadioButton rb_cook;
    private RadioButton rb_food;
    private FragmentManager fragmentManager;

    private Fragment[] fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_life, null);
            rb_cook = (RadioButton) root.findViewById(R.id.fragment_life_rb_cook);
            rb_cook.setOnCheckedChangeListener(this);
            rb_food = (RadioButton) root.findViewById(R.id.fragment_life_rb_food);
            rb_food.setOnCheckedChangeListener(this);
            rb_cook.setChecked(true);
        }

        if (fragmentManager == null) {
            fragmentManager = getChildFragmentManager();
        }
        if (fragments == null) {
            fragments = new Fragment[2];
        }
        if (fragments[0] == null)
            fragments[0] = new CookFragment();
        if (fragments[1] == null)
            fragments[1] = new FoodFragment();

        onCheckedChanged(rb_cook, true);

        return root;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (compoundButton == rb_food) {//跳转到健食物
                fragmentTransaction.replace(R.id.fragment_life_content, fragments[1]);
            } else {//跳转到健康菜谱
                fragmentTransaction.replace(R.id.fragment_life_content, fragments[0]);
            }
            fragmentTransaction.commit();
        }
    }



}
