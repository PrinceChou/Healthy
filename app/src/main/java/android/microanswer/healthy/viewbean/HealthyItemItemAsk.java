package android.microanswer.healthy.viewbean;

import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.AskClassifyItem;
import android.microanswer.healthy.bean.AskListItem;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 由 Micro 创建于 2016/6/24.
 */

public class HealthyItemItemAsk extends RecyclerView.ViewHolder {
    private TextView[] tv_names;


    public HealthyItemItemAsk(View itemView) {
        super(itemView);
        tv_names = new TextView[4];
        tv_names[0] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_question$ask1);
        tv_names[1] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_question$ask2);
        tv_names[2] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_question$ask3);
        tv_names[3] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_question$ask4);
    }

    public void setData(List<AskClassifyItem> data) {

        for (int i = 0; i < data.size(); i++) {
            tv_names[i].setText(data.get(i).getTitle());
        }
    }
}