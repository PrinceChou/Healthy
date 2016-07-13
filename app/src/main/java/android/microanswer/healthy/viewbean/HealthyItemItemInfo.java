package android.microanswer.healthy.viewbean;

import android.content.Context;
import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.InfoListItem;
import android.microanswer.healthy.bean.LoreListItem;
import android.microanswer.healthy.tools.BaseTools;
import android.microanswer.healthy.tools.JavaBeanTools;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * 由 Micro 创建于 2016/6/24.
 */

public class HealthyItemItemInfo extends RecyclerView.ViewHolder {
    private TextView tv_names[];
    private ImageView iv_imgs[];
    private Context context;

    public HealthyItemItemInfo(View itemView,Context context) {
        super(itemView);
        this.context = context;
        tv_names = new TextView[2];
        iv_imgs = new ImageView[2];
        tv_names[0] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_knowledge_desc1);
        tv_names[1] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_knowledge_desc2);
        iv_imgs[0] = (ImageView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_knowledge_img1);
        iv_imgs[1] = (ImageView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_knowledge_img2);
    }

    public void setData(ArrayList<InfoListItem> data) {
        for (int i = 0; i < data.size(); i++) {
            InfoListItem item = data.get(i);
            tv_names[i].setText(item.getTitle());
            if (item.getImg() != null)
                ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + item.getImg(), iv_imgs[i]);
        }
    }


}
