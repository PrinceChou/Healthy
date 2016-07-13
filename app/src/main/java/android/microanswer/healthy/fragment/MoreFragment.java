package android.microanswer.healthy.fragment;

import android.microanswer.healthy.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 由 Micro 创建于 2016/6/30.
 */

public class MoreFragment extends Fragment {
    private View root = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_more, null);
        return root;
    }
}
