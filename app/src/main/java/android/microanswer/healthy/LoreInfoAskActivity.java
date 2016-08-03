package android.microanswer.healthy;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.microanswer.healthy.bean.AskListItem;
import android.microanswer.healthy.bean.InfoListItem;
import android.microanswer.healthy.bean.LoreListItem;
import android.microanswer.healthy.bean.User;
import android.microanswer.healthy.tools.BaseTools;
import android.microanswer.healthy.tools.InternetServiceTool;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 由 Micro 创建于 2016/8/1.
 */

public class LoreInfoAskActivity extends BaseActivity implements View.OnClickListener {

    private LoreListItem loreListItem;
    private InfoListItem infoListItem;
    private AskListItem askListItem;


    private TextView title;
    private TextView time;
    private TextView content;

    private TextView tv_like;
    private TextView tv_read;
    private TextView tv_say;

    private ImageView img;

    private LinearLayout pinglunListConent;//评论列表容器


    private View loadingview;
    private View dataview;

    private EditText pinlunbox;
    private TextView sendpinlun;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lore);
        suitToolBar(R.id.activity_lore_toolbar);
        setToolBarBackEnable();
        final Serializable data = getIntent().getSerializableExtra("data");
        if (data instanceof AskListItem) {
            askListItem = (AskListItem) data;
        } else if (data instanceof InfoListItem) {
            infoListItem = (InfoListItem) data;
        } else if (data instanceof LoreListItem) {
            loreListItem = (LoreListItem) data;
        }


        title = (TextView) findViewById(R.id.activity_lore_title);
        time = (TextView) findViewById(R.id.activity_lore_time);
        content = (TextView) findViewById(R.id.activity_lore_content);
        img = (ImageView) findViewById(R.id.activity_lore_img);

        tv_like = (TextView) findViewById(R.id.activity_lore_likecount);
        tv_read = (TextView) findViewById(R.id.activity_lore_readcount);
        tv_say = (TextView) findViewById(R.id.activity_lore_saycount);

        pinlunbox = (EditText) findViewById(R.id.activity_lore_pinlun_box);
        pinglunListConent = (LinearLayout) findViewById(R.id.activity_lore_pinlunlistcontent);
        sendpinlun = (TextView) findViewById(R.id.activity_lore_sendpinlun);
        sendpinlun.setOnClickListener(this);


        loadingview = findViewById(R.id.activity_lore_loadingview);
        loadingview.setVisibility(View.VISIBLE);
        dataview = findViewById(R.id.activity_lore_dataview);
        dataview.setVisibility(View.GONE);


        runOnOtherThread(new BaseOtherThread() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            void onOtherThreadRunEnd(Message msg) {


                if (msg.obj == null) {
                    loadingview.setVisibility(View.GONE);
                    alertDialog("加载失败", "可能是因为你的网诺环境不好，请稍后重试").show();
                    return;
                }

                String fcount = "";
                String rcount = "";
                String count = "";
                String timea = "";
                String message = "";
                String titlea = "";
                String imga = "";

                if (msg.obj instanceof AskListItem) {
                    AskListItem ai = (AskListItem) msg.obj;
                    fcount = ai.getFcount() + "";
                    rcount = ai.getRcount() + "";
                    count = ai.getCount() + "";
                    titlea = ai.getTitle();
                    timea = new Date(ai.getTime()).toLocaleString();
                    ai.setMessage(ai.getMessage().replace("<p>", "<p>　　"));
                    message = ai.getMessage();
                    imga = ai.getImg();
                } else if (msg.obj instanceof LoreListItem) {
                    LoreListItem ll = (LoreListItem) msg.obj;
                    fcount = ll.getFcount() + "";
                    rcount = ll.getRcount() + "";
                    count = ll.getCount() + "";
                    titlea = ll.getTitle();
                    timea = new Date(ll.getTime()).toLocaleString();
                    ll.setMessage(ll.getMessage().replace("<p>", "<p>　　"));
                    message = ll.getMessage();
                    imga = ll.getImg();
                } else if (msg.obj instanceof InfoListItem) {
                    InfoListItem il = (InfoListItem) msg.obj;
                    fcount = il.getFcount() + "";
                    rcount = il.getRcount() + "";
                    count = il.getCount() + "";
                    titlea = il.getTitle();
                    timea = new Date(il.getTime()).toLocaleString();
                    il.setMessage(il.getMessage().replace("<p>", "<p>　　"));
                    message = il.getMessage();
                    imga = il.getImg();
                }


                tv_like.setText("收藏量:" + fcount);
                tv_say.setText("评论量:" + rcount);
                tv_read.setText("访问量:" + count);
                title.setText(titlea);
                time.setText("发布时间：" + timea);

                final SpannableStringBuilder ssb = (SpannableStringBuilder) Html.fromHtml(message, null, null);
                dataview.setVisibility(View.VISIBLE);
                loadingview.setVisibility(View.GONE);
                loadPinlun();
                content.setText(ssb);
                ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + imga, img);
                //使用XMLreader实现html解析
                if (true) {
                    return;
                }


                ImageSpan[] spans = ssb.getSpans(0, ssb.length(), ImageSpan.class);

                if (spans.length > 0) {
                    dataview.setVisibility(View.VISIBLE);
                    loadingview.setVisibility(View.GONE);
                    loadPinlun();
                    for (ImageSpan is : spans) {
                        final ImageSpan imageSpan = is;
                        ImageLoader.getInstance().loadImage(imageSpan.getSource(), new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {

                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                            }

                            @Override
                            public synchronized void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
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
                                try {
                                ssb.setSpan(imageSpan, ssb.getSpanStart(imageSpan), ssb.getSpanEnd(imageSpan), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    content.setText(ssb);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {

                            }
                        });
                    }
                } else {
                    dataview.setVisibility(View.VISIBLE);
                    loadingview.setVisibility(View.GONE);
                    loadPinlun();
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
                if (data instanceof LoreListItem) {
                    LoreListItem loreShow = JavaBeanTools.Lore.getLoreShow((int) loreListItem.getId());
                    Message msg = new Message();
                    msg.obj = loreShow;
                    return msg;
                } else if (data instanceof AskListItem) {
                    AskListItem askListIte = JavaBeanTools.Ask.getAskShow((int) askListItem.getId());
                    Message msg = new Message();
                    msg.obj = askListIte;
                    return msg;
                } else if (data instanceof InfoListItem) {
                    InfoListItem infoitem = JavaBeanTools.Info.getInfo(infoListItem.getId());
                    Message msg = new Message();
                    msg.obj = infoitem;
                    return msg;
                }
                return new Message();
            }
        }, 6675);


    }


    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return super.onHomeButtonClick();
    }


    /**
     * 加载评论列表
     */
    private void loadPinlun() {
        runOnOtherThread(new BaseOtherThread() {
            @Override
            void onOtherThreadRunEnd(Message msg) {
                if (msg != null) {
                    List<PinLun> pinLuns = (List<PinLun>) msg.obj;
                    pinglunListConent.removeAllViews();
                    TextView ttitle = new TextView(LoreInfoAskActivity.this);
                    ttitle.setText("总共" + pinLuns.size() + "条评论");
                    int i = BaseTools.Dp2Px(LoreInfoAskActivity.this, 7f);
                    ttitle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    ttitle.setTextColor(Color.WHITE);
                    ttitle.setPadding(i, i, i, i);
                    pinglunListConent.addView(ttitle);
                    for (PinLun pinLun : pinLuns) {
                        View v = View.inflate(LoreInfoAskActivity.this, R.layout.view_pinlun_item, null);
                        ImageView img = (ImageView) v.findViewById(R.id.view_pinlun_img);
                        TextView account = (TextView) v.findViewById(R.id.view_pinlun_account);
                        TextView time = (TextView) v.findViewById(R.id.view_pinlun_time);
                        TextView content = (TextView) v.findViewById(R.id.view_pinlun_content);
                        ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + pinLun.getAvatar(), img);
                        account.setText(pinLun.getAccount());
                        time.setText(new Date(pinLun.getTime()).toLocaleString());
                        content.setText(pinLun.getMemo());
                        pinglunListConent.addView(v);
                    }
                }
            }

            @Override
            public Map getTaskParams() {
                HashMap<String, Object> p = new HashMap<String, Object>();

                int id = (int) (loreListItem != null ? loreListItem.getId() : askListItem != null ? askListItem.getId() : infoListItem.getId());
                String type = (loreListItem != null ? TYPE_LORE : askListItem != null ? TYPE_ASK : TYPE_INFO);
                p.put("id", id);
                p.put("type", type);
                return p;
            }

            @Override
            public Message run(Map params) {
                String type = (String) params.get("type");
                Object id = params.get("id");

                String urll = "http://www.tngou.net/api/memo/comment?id=" + id + "&type=" + type;

                String request = InternetServiceTool.request(urll);

                JSONObject jsonObject = JSON.parseObject(request);

                if (jsonObject.getBooleanValue("status")) {
                    int count = jsonObject.getInteger("total");
                    if (count > 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray("tngou");
                        List<PinLun> pinLuns = JSON.parseArray(jsonArray.toJSONString(), PinLun.class);
                        Message msg = new Message();
                        msg.obj = pinLuns;
                        return msg;
                    }
                }


//                Log.i("loadPinlun", request);


                return null;
            }
        }, 6678);
    }

    @Override
    public void onClick(View view) {
        if (view == sendpinlun) {
            //发送评论
            sendPinlun();
        }
    }

    private boolean isPinlunging = false;//标记是否正在评论

    private void sendPinlun() {
        if (isPinlunging) {
            return;
        }
        final String pinluncontent = pinlunbox.getText().toString().trim();
        if (isTextEmpty(pinluncontent)) {
            toast("评论为空", POSOTION_TOP);
        } else {
            if (isTextEmpty(User.getUser().getAccess_token()) || isTextEmpty(User.getUser().getRefresh_token())) {
                alertDialog("未登录", "请先登录后再做评论").show();
            } else {
                runOnOtherThread(new BaseOtherThread() {
                    @Override
                    void onOtherThreadRunEnd(Message msg) {
                        if (msg != null) {
                            View lastchild = pinglunListConent.getChildAt(pinglunListConent.getChildCount() - 1);
                            if (lastchild != null && lastchild instanceof TextView) {
                                pinglunListConent.removeAllViews();
                                TextView ttitle = new TextView(LoreInfoAskActivity.this);
                                ttitle.setText("总共1条评论");
                                int i = BaseTools.Dp2Px(LoreInfoAskActivity.this, 7f);
                                ttitle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                ttitle.setTextColor(Color.WHITE);
                                ttitle.setPadding(i, i, i, i);
                                pinglunListConent.addView(ttitle);
                            }

                            View v = View.inflate(LoreInfoAskActivity.this, R.layout.view_pinlun_item, null);
                            ImageView img = (ImageView) v.findViewById(R.id.view_pinlun_img);
                            TextView account = (TextView) v.findViewById(R.id.view_pinlun_account);
                            TextView time = (TextView) v.findViewById(R.id.view_pinlun_time);
                            TextView content = (TextView) v.findViewById(R.id.view_pinlun_content);
                            ImageLoader.getInstance().displayImage("http://tnfs.tngou.net/image" + User.getUser().getAvatar(), img);
                            account.setText(User.getUser().getAccount());
                            time.setText(new Date().toLocaleString());
                            content.setText(pinluncontent);
                            pinglunListConent.addView(v);
                            TextView childAt0 = (TextView) pinglunListConent.getChildAt(0);
                            childAt0.setText("共" + (pinglunListConent.getChildCount() - 1) + "条评论");
                            pinlunbox.setText("");
                        } else {
                            toast("评论失败", POSOTION_TOP);
                        }
                        isPinlunging = false;
                        sendpinlun.setEnabled(true);
                    }

                    @Override
                    public Map getTaskParams() {
                        isPinlunging = true;
                        sendpinlun.setEnabled(false);
                        String url = null;
                        try {
                            url = "http://www.tngou.net/api/memo/add?access_token=" + User.getUser().getAccess_token() + "&oid=" + loreListItem.getId() + "&otype=" + TYPE_LORE + "&title=" + URLEncoder.encode(loreListItem.getTitle(), "UTF-8") + "&memo=" + URLEncoder.encode(pinluncontent, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        HashMap<String, String> pa = new HashMap<String, String>();
                        pa.put("url", url);
                        return pa;
                    }

                    @Override
                    public Message run(Map params) {
                        String data = InternetServiceTool.request(params.get("url") + "");
                        Log.i("留言", data);
                        JSONObject jsonObject = JSON.parseObject(data);
                        if (jsonObject.getBooleanValue("status")) {
                            return new Message();
                        }
                        return null;
                    }
                }, 76643);

            }
        }
    }


    public static class PinLun implements Serializable {

        /**
         * account : Answer
         * avatar : /avatar/160720/1569.png
         * domain : microanswe
         * id : 1647
         * memo : 我爱评论，效果好好
         * oid : 19801
         * otype : lore
         * pid : 0
         * rcount : 0
         * time : 1470118943000
         * title : 土豆片蕴含的淀粉能有效减缓股癣的瘙痒和疼痛
         * url : http://www.tngou.net/lore/show/19801
         * user : 1569
         */

        private String account;//登录帐号
        private String avatar;//头像地址
        private String domain;//主页地址
        private int id;
        private String memo;//留言内容
        private int oid;//对象id
        private String otype;//对象内容
        private int pid;//保留
        private int rcount;
        private long time;
        private String title;//回复内容的标题
        private String url;//回复内容的URL地址
        private int user;//用户id

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public String getOtype() {
            return otype;
        }

        public void setOtype(String otype) {
            this.otype = otype;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }
    }
}
