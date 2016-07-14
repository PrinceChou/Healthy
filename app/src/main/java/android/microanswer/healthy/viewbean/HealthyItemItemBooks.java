package android.microanswer.healthy.viewbean;

import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.BookListItem;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 由 Micro 创建于 2016/6/24.
 */

public class HealthyItemItemBooks extends RecyclerView.ViewHolder {
    private TextView[] tv_names;
    private ImageView[] iv_imgs;

    public HealthyItemItemBooks(View itemView) {
        super(itemView);
        tv_names = new TextView[3];
        iv_imgs = new ImageView[3];
        tv_names[0] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_books_title1);
        tv_names[1] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_books_title2);
        tv_names[2] = (TextView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_books_title3);
        iv_imgs[0] = (ImageView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_books_img1);
        iv_imgs[1] = (ImageView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_books_img2);
        iv_imgs[2] = (ImageView) itemView.findViewById(R.id.viewpager_healthy_itemitem_health_books_img3);
    }

    public void setData(List<BookListItem> data) {
        for (int i = 0; i < data.size(); i++) {
            BookListItem item = data.get(i);
            tv_names[i].setText(item.getName());
            if (item.getImg() != null) {
                ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + item.getImg(), iv_imgs[i]);
            }
        }

    }


}
