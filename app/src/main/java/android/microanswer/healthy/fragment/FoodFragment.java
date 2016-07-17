package android.microanswer.healthy.fragment;

import android.microanswer.healthy.R;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 由 Micro 创建于 2016/7/17.
 */

public class FoodFragment extends Fragment {
    private View rootview;
    private TabLayout tabLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_cook, null);
        }
        if (tabLayout == null) {
            tabLayout = (TabLayout) rootview.findViewById(R.id.fragment_cook_tablayout);
        }

        if (tabLayout.getTabCount() < 20) {
            for (int i = 0; i < 20; i++) {
                tabLayout.addTab(tabLayout.newTab().setText("tab" + (i + 1)));
            }
        }

        return rootview;
    }
}
