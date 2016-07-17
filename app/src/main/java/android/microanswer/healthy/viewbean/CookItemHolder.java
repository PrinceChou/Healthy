package android.microanswer.healthy.viewbean;

import android.microanswer.healthy.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 由 Micro 创建于 2016/7/18.
 */

public class CookItemHolder extends RecyclerView.ViewHolder {
    private ImageView img;
    private TextView tv;

    public CookItemHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.view_cook_item_img);
        tv = (TextView) itemView.findViewById(R.id.view_cook_item_title);
    }


    public void setImg(String url) {
        ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + url, img);
    }

    public void setTitle(String title) {
        this.tv.setText(title);
    }

}
