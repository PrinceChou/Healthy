package android.microanswer.healthy.fragment;

import android.microanswer.healthy.R;
import android.microanswer.healthy.adapter.MoreGridviewAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * 由 Micro 创建于 2016/6/30.
 */

public class MoreFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View root = null;
    private GridView gridView;
    private MoreGridviewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_more, null);
        adapter = new MoreGridviewAdapter(getActivity());
        gridView = (GridView) root.findViewById(R.id.fragment_more_gridview);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_shake));
    }
}
