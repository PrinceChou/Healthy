package android.microanswer.healthy;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.microanswer.healthy.application.Healthy;
import android.microanswer.healthy.bean.CookListItem;
import android.microanswer.healthy.tools.BaseTools;
import android.microanswer.healthy.view.HtmlView;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康菜谱,详细信息
 * 由 Micro 创建于 2016/8/12.
 */

public class CookActivity extends BaseActivity {
    private CookListItem cookListItem;

    private View cook_img_bg;
    private ImageView cook_img;
    private TextView cook_title;
    private TextView cook_desc;
    private TextView cook_like$dislike;
    private LinearLayout key_words_content;
    private HtmlView cook_htmlview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        suitToolBar(R.id.activity_cook_toolbar);
        setToolBarBackEnable();
        cook_img_bg = findViewById(R.id.activity_cook_head_relativelayout);
        cook_desc = (TextView) findViewById(R.id.activity_cook_desc);
        cook_img = (ImageView) findViewById(R.id.activity_cook_img);
        cook_title = (TextView) findViewById(R.id.activity_cook_title);
        cook_like$dislike = (TextView) findViewById(R.id.activity_cook_like$dislike);
        key_words_content = (LinearLayout) findViewById(R.id.activity_cook_keywords);
        cook_htmlview = (HtmlView) findViewById(R.id.activity_cook_htmlview);


        cookListItem = (CookListItem) getIntent().getSerializableExtra("data");

        if (cookListItem != null) {
            ImageLoader.getInstance().displayImage(Healthy.IMAGE_URL + cookListItem.getImg(), cook_img, new CookImageLoadListener());
            cook_desc.setText(cookListItem.getDescription());
            cook_title.setText(cookListItem.getName());
        }
    }

    /**
     * 图片加载监听
     */
    private class CookImageLoadListener implements ImageLoadingListener {

        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            runOnOtherThread(new DoCookImgBg(loadedImage), 229);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }
    }

    /**
     * 模糊背景操作
     */
    private class DoCookImgBg extends BaseOtherThread {

        private Bitmap bitmap;

        private DoCookImgBg(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        void onOtherThreadRunEnd(Message msg) {
            cook_img_bg.setBackgroundDrawable(new BitmapDrawable((Bitmap) msg.obj));
        }

        @Override
        public Map getTaskParams() {
            HashMap<String, Bitmap> map = new HashMap<>();
            map.put("data", this.bitmap);
            return map;
        }

        @Override
        public Message run(Map params) {
            Message msg = new Message();
            msg.obj = BaseTools.doBlur2((Bitmap) params.get("data"));
            return msg;
        }
    }


    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return super.onHomeButtonClick();
    }
}
