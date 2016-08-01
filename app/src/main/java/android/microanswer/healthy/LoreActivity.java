package android.microanswer.healthy;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.microanswer.healthy.bean.LoreListItem;
import android.microanswer.healthy.tools.JavaBeanTools;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

/**
 * 由 Micro 创建于 2016/8/1.
 */

public class LoreActivity extends BaseActivity {

    private LoreListItem loreListItem;

    private TextView title;
    private TextView time;
    private TextView content;
//    private ImageView img;

    private TextView tv_like;
    private TextView tv_read;
    private TextView tv_say;

//    private BitmapDrawable drawable;

    private View loadingview;
    private View dataview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lore);
        suitToolBar(R.id.activity_lore_toolbar);
        setToolBarBackEnable();
        loreListItem = (LoreListItem) getIntent().getSerializableExtra("data");

        title = (TextView) findViewById(R.id.activity_lore_title);
        time = (TextView) findViewById(R.id.activity_lore_time);
        content = (TextView) findViewById(R.id.activity_lore_content);
//        img = (ImageView) findViewById(R.id.activity_lore_img);

        tv_like = (TextView) findViewById(R.id.activity_lore_likecount);
        tv_read = (TextView) findViewById(R.id.activity_lore_readcount);
        tv_say = (TextView) findViewById(R.id.activity_lore_saycount);


        loadingview = findViewById(R.id.activity_lore_loadingview);
        loadingview.setVisibility(View.VISIBLE);
        dataview = findViewById(R.id.activity_lore_dataview);
        dataview.setVisibility(View.GONE);
//        drawable = new BitmapDrawable();
//        drawable.setBounds(0, 0, getScreenWidth(), BaseTools.Dp2Px(this, 130f));


        runOnOtherThread(new BaseOtherThread() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            void onOtherThreadRunEnd(Message msg) {
                loreListItem = (LoreListItem) msg.obj;

                tv_like.setText("访问量:" + loreListItem.getFcount());
                tv_say.setText("评论量:" + loreListItem.getRcount());
                tv_read.setText("访问量:" + loreListItem.getCount());
                title.setText(loreListItem.getTitle());
                time.setText("发布时间：" + new Date(loreListItem.getTime()).toLocaleString());
                loreListItem.setMessage(loreListItem.getMessage().replace("<p>", "<p>　　"));

                final SpannableStringBuilder ssb = (SpannableStringBuilder) Html.fromHtml(loreListItem.getMessage(), null, null);

                ImageSpan[] spans = ssb.getSpans(0, ssb.length(), ImageSpan.class);

                if (spans.length > 0) {
                    final ImageSpan imageSpan = spans[0];
                    ImageLoader.getInstance().loadImage(imageSpan.getSource(), new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            dataview.setVisibility(View.VISIBLE);
                            loadingview.setVisibility(View.GONE);

                            try {
                                Field mDrawable = ImageSpan.class.getDeclaredField("mDrawable");
                                mDrawable.setAccessible(true);
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), loadedImage);
                                float sc = getScreenWidth() / (float) loadedImage.getWidth();

                                bitmapDrawable.setBounds(0, 0, getScreenWidth(), (int) (loadedImage.getHeight() * sc));
                                mDrawable.set(imageSpan, bitmapDrawable);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            ssb.setSpan(imageSpan, ssb.getSpanStart(imageSpan), ssb.getSpanEnd(imageSpan), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            content.setText(ssb);
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });

                } else {
                    dataview.setVisibility(View.VISIBLE);
                    loadingview.setVisibility(View.GONE);
                    content.setText(ssb);
                }
//                ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + loreListItem.getImg(), img);
            }

            @Override
            public Map getTaskParams() {
                return null;
            }

            @Override
            public Message run(Map params) {

                LoreListItem loreShow = JavaBeanTools.Lore.getLoreShow((int) loreListItem.getId());
                Message msg = new Message();
                msg.obj = loreShow;
                return msg;
            }
        }, 6675);


    }


    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return super.onHomeButtonClick();
    }
}
