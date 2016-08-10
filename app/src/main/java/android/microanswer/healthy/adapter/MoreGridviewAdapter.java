package android.microanswer.healthy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.graphics.ColorUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 由 Micro 创建于 2016/8/10.
 */

public class MoreGridviewAdapter extends BaseAdapter {


    private final String[] items = {
            "药品信息", "疾病信息", "病状信息", "手术项目",
            "检查项目", "医院门诊", "药企药厂", "药店药房",
            "农业新闻", "农业技术", "博客", "论坛",
            "业务", "社会热点", "天狗阅图", "农政揽要",
            "地方快报", "农贸经济", "农业科技", "三农人文",
            "农机农品", "创业推广", "农牧渔林", "手术项目",
            "粮食种植", "家禽养殖", "畜牧养殖", "更多"
    };

    private final int[] colors = {
            0x112ffa, 0x11a1f4, 0x13f23e, 0x11a533,
            0xaa2233, 0x1cc12f, 0x1cfa43, 0xdd3a33,
            0x19fac3, 0x1ace33, 0x1122ff, 0x113aed,
            0xa12233, 0x1fa2c3, 0xaced25, 0x6cf3d1,
            0xaac51d, 0x2deaa2, 0x66fdd2, 0x1ffdda,
            0xaa34d1, 0x2dfc33, 0x1a16d3, 0xfaff8a,
            0x1add33, 0x4aaaa3, 0xeee233, 0x2d3fe3,
    };

    private Context context;

    public MoreGridviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv = null;
        if (view == null) {
            tv = new TextView(context);
            tv.setLayoutParams(new ViewGroup.LayoutParams(viewGroup.getWidth() / 4, viewGroup.getWidth() / 4));
            tv.setGravity(Gravity.CENTER);
            view = tv;
        } else {
            tv = (TextView) view;
        }

        tv.setBackgroundColor(Color.rgb((colors[i] & 0xff0000) >> 16, (colors[i] & 0x00ff00) >> 8, colors[i] & 0x0000ff));
        tv.setText(items[i]);

        return view;
    }
}
