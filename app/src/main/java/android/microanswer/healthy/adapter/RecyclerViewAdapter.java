package android.microanswer.healthy.adapter;

import android.content.Context;
import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.AskClassifyItem;
import android.microanswer.healthy.bean.BookListItem;
import android.microanswer.healthy.bean.InfoListItem;
import android.microanswer.healthy.bean.LoreListItem;
import android.microanswer.healthy.exception.JavaBeanDataLoadException;
import android.microanswer.healthy.tools.JavaBeanTools;
import android.microanswer.healthy.viewbean.BannerViewHolder;
import android.microanswer.healthy.viewbean.HealthyItemGroup;
import android.microanswer.healthy.viewbean.HealthyItemItemAsk;
import android.microanswer.healthy.viewbean.HealthyItemItemBooks;
import android.microanswer.healthy.viewbean.HealthyItemItemInfo;
import android.microanswer.healthy.viewbean.HealthyItemItemKnowledge;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 由 Micro 创建于 2016/6/30.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    public static final int TYPE_BANNER = 1;
    public static final int TYPE_ITEMGROUP = 2;
    public static final int TYPE_ITEM_KNOWLEDGE = 3;
    public static final int TYPE_ITEM_BOOKS = 4;
    public static final int TYPE_ITEM_ASK = 5;
    public static final int TYPE_ITEM_INFO = 6;


    public static final int FUNCTION_NORMALLOAD = 1;
    public static final int FUNCTION_REFRESH = 2;

    private static final int WHAT_ITEMUPDATE = 1;
    private static final int WHAT_REFRESH_END = 2;
    private static final int WHAT_START_AUTO_REFRESH = 3;
    private static final int WHAT_ITEM_APPEND = 4;
    private static final int WHAT_ERROR = 5;

    private FragmentManager fragmentmanager;
    private Context context;


    /**
     * 数据格式：<br/>
     * ArrayList<Map<String,Object>><br/>
     * Map<String,Object><br/>
     * ["type":"type-value"]<br/>
     * ["data":"item-data"]<br/>
     */
    private ArrayList<Map<String, Object>> data;

    public RecyclerViewAdapter(Context ccontext, FragmentManager fragmentmanager) {
        this.context = ccontext;
        data = generateDisOnlineData();
        this.fragmentmanager = fragmentmanager;
    }

    /**
     * 数据生成方法，用于没有加载完成数据的时候显示
     *
     * @return
     */
    private ArrayList<Map<String, Object>> generateDisOnlineData() {
        ArrayList<Map<String, Object>> datalist = new ArrayList<>();

        Map<String, Object> banner = new HashMap<>();
        banner.put("type", TYPE_BANNER);
        banner.put("function", FUNCTION_NORMALLOAD);
        datalist.add(banner);
        //构建Banner

        Map<String, Object> group1 = new HashMap<>();
        group1.put("type", TYPE_ITEMGROUP);
        group1.put("name", "健康知识");
        group1.put("smallname", "Health Knowledge");
        datalist.add(group1);
        //构建健康知识标题

        for (int i = 0; i < 2; i++) {
            Map<String, Object> knowledgedata = new HashMap<>();
            knowledgedata.put("type", TYPE_ITEM_KNOWLEDGE);
            ArrayList<LoreListItem> item_in_item = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                LoreListItem loreListItem = new LoreListItem();
                loreListItem.setDescription("加载中...");
                item_in_item.add(loreListItem);
            }
            knowledgedata.put("data", item_in_item);
            datalist.add(knowledgedata);
        }//构建健康知识数据

        Map<String, Object> group2 = new HashMap<>();
        group2.put("type", TYPE_ITEMGROUP);
        group2.put("name", "健康问答");
        group2.put("smallname", "Health Question & Answer");
        datalist.add(group2);
        //构建健康问答标题

        for (int m = 0; m < 3; m++) {
            Map<String, Object> askdata = new HashMap<>();
            askdata.put("type", TYPE_ITEM_ASK);
            ArrayList<AskClassifyItem> item_in_item = new ArrayList<>();
            for (int l = 0; l < 4; l++) {
                AskClassifyItem askListItem = new AskClassifyItem();
                askListItem.setTitle("加载中..");
                item_in_item.add(askListItem);
            }
            askdata.put("data", item_in_item);
            datalist.add(askdata);
        }//构建健康问答数据

        Map<String, Object> group3 = new HashMap<>();
        group3.put("type", TYPE_ITEMGROUP);
        group3.put("name", "健康图书");
        group3.put("smallname", "Health Books");
        datalist.add(group3);
        //构建健康问答标题

        for (int m = 0; m < 3; m++) {
            Map<String, Object> bookdata = new HashMap<>();
            bookdata.put("type", TYPE_ITEM_BOOKS);
            ArrayList<BookListItem> item_in_item = new ArrayList<>();
            for (int l = 0; l < 3; l++) {
                BookListItem bookListItem = new BookListItem();
                bookListItem.setName("加载中...");
                item_in_item.add(bookListItem);
            }
            bookdata.put("data", item_in_item);
            datalist.add(bookdata);
        }//构建健康问答数据

        Map<String, Object> group4 = new HashMap<>();
        group4.put("type", TYPE_ITEMGROUP);
        group4.put("name", "更多资讯");
        group4.put("smallname", "More Healthy Info");
        datalist.add(group4);
        //构建更多资讯标题

        for (int i = 0; i < 3; i++) {
            Map<String, Object> Infolistdata = new HashMap<>();
            Infolistdata.put("type", TYPE_ITEM_INFO);
            ArrayList<InfoListItem> item_in_item = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                InfoListItem infoListItem = new InfoListItem();
                infoListItem.setDescription("加载中...");
                item_in_item.add(infoListItem);
            }
            Infolistdata.put("data", item_in_item);
            datalist.add(Infolistdata);
        }//构建更多数据
        infoPage = 1;
        return datalist;
    }

    private int infoPage = 1;
    private boolean isappending = false;

    public void appendHealthyInfo() {
        if (isappending) {//正在添加新内容，不要再添加
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                isappending = true;
                Map<String, Object> itemObject = getItemObject(getItemCount() - 1);
                ArrayList<InfoListItem> itemdatas = (ArrayList<InfoListItem>) itemObject.get("data");
                InfoListItem lastinfo = itemdatas.get(1);
                int startindex = getItemCount() + 1;
                int lastinfoclass = lastinfo.getInfoclass();
                for (int i = 0; i < 3; i++) {
                    Message msg = Message.obtain(handler);
                    msg.what = WHAT_ITEM_APPEND;
                    Map<String, Object> item = new HashMap<String, Object>();
                    item.put("type", TYPE_ITEM_INFO);
                    try {
                        ArrayList<InfoListItem> data = JavaBeanTools.Info.getInfoListData(2, ++infoPage, lastinfoclass, null);
                        item.put("data", data);
                    } catch (JavaBeanDataLoadException e) {
                        e.printStackTrace();
                        Message msge = Message.obtain(handler);
                        msge.what = WHAT_ERROR;
                        msge.obj = "你的网络好像不怎么好！";
                        msge.sendToTarget();
                        isappending = false;
                        return;
                    }
                    msg.obj = item;
                    msg.sendToTarget();
                }
                isappending = false;
            }
        }).start();
    }


    /**
     * 生成在线数据，从网络获取数据
     */
    public void generateOnlineData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Random rd = new Random();

                Map<String, Object> banner = new HashMap<>();
                banner.put("type", TYPE_BANNER);
                banner.put("function", FUNCTION_REFRESH);
                Message msg0 = handler.obtainMessage();
                msg0.what = WHAT_ITEMUPDATE;
                msg0.obj = banner;
                msg0.arg1 = 0;
                handler.sendMessage(msg0);
                //刷新banner数据


                try {
                    int loreClassID = rd.nextInt(7);
                    for (int i = 0; i < 2; i++) {
                        ArrayList<LoreListItem> loreListData = JavaBeanTools.Lore.getLoreListData(2, 1 + i, loreClassID, null);
                        HashMap<String, Object> items = new HashMap<String, Object>();
                        items.put("type", TYPE_ITEM_KNOWLEDGE);
                        items.put("data", loreListData);
                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_ITEMUPDATE;
                        msg.obj = items;
                        msg.arg1 = (i + 2);
                        handler.sendMessage(msg);
                    }//请求健康知识数据

                    ArrayList<AskClassifyItem> askClassifyData = JavaBeanTools.Ask.getAskClassifyData(null);


                    for (int j = 0; j < 3; j++) {
                        ArrayList<AskClassifyItem> itemAskData = new ArrayList<AskClassifyItem>();
                        Map<String, Object> items = new HashMap<String, Object>();
                        for (int g = 0; g < 4; g++) {
                            if (askClassifyData.size() > 0) {
                                itemAskData.add(askClassifyData.remove(0));
                            }
                        }
                        items.put("type", TYPE_ITEM_ASK);
                        items.put("data", itemAskData);
                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_ITEMUPDATE;
                        msg.arg1 = (j + 5);
                        msg.obj = items;
                        handler.sendMessage(msg);
                    }//请求健康问答数据

                    int id = rd.nextInt(7);
                    for (int j = 0; j < 3; j++) {
                        ArrayList<BookListItem> itembookData = JavaBeanTools.Book.getBookListData(3, (1 + j), id, null);
                        Map<String, Object> items = new HashMap<String, Object>();
                        items.put("type", TYPE_ITEM_BOOKS);
                        items.put("data", itembookData);
                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_ITEMUPDATE;
                        msg.arg1 = (j + 9);
                        msg.obj = items;
                        handler.sendMessage(msg);
                    }//请求健康图书数据


                    for (int i = 0; i < 3; i++) {
                        Map<String, Object> infoitemdata = new HashMap<>();
                        infoPage = (1 + i);
                        ArrayList<InfoListItem> infoListData = JavaBeanTools.Info.getInfoListData(2, infoPage, rd.nextInt(7) + 1, null);
                        infoitemdata.put("type", TYPE_ITEM_INFO);
                        infoitemdata.put("data", infoListData);
                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_ITEMUPDATE;
                        msg.arg1 = (i + 13);
                        msg.obj = infoitemdata;
                        msg.sendToTarget();
                    }//构建更多资讯数据


                } catch (Exception e) {
                    e.printStackTrace();
                    Message msge = handler.obtainMessage();
                    msge.what = WHAT_ERROR;
                    msge.obj = "你的网络好像不怎么好！";
                    msge.sendToTarget();
                }

                handler.sendEmptyMessage(WHAT_REFRESH_END);
            }
        }).start();
    }

    public void updateData(int index, Map<String, Object> dataitem) {
        this.data.set(index, dataitem);
        this.notifyItemChanged(index);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i("RecyclerViewAdapter", "onCreateViewHolder,parent=" + parent.getClass().getSimpleName() + ",viewType=" + viewType);
        if (viewType == TYPE_BANNER) {
            View bannerview = View.inflate(context, R.layout.viewpager_healthy_banner, null);
            return new BannerViewHolder(bannerview, context, fragmentmanager);
        } else if (viewType == TYPE_ITEMGROUP) {
            View itemGroupView = View.inflate(context, R.layout.viewpager_healthy_itemgroup, null);
            return new HealthyItemGroup(itemGroupView);
        } else if (viewType == TYPE_ITEM_ASK) {
            View itemitem = View.inflate(context, R.layout.viewpager_healthy_itemitem_ask, null);
            HealthyItemItemAsk healthyItemItem = new HealthyItemItemAsk(itemitem);
            return healthyItemItem;
        } else if (viewType == TYPE_ITEM_BOOKS) {
            return new HealthyItemItemBooks(View.inflate(context, R.layout.viewpager_healthy_itemitem_health_books, null));
        } else if (viewType == TYPE_ITEM_KNOWLEDGE) {
            return new HealthyItemItemKnowledge(View.inflate(context, R.layout.viewpager_healthy_itemitem_health_knowledge, null));
        } else if (viewType == TYPE_ITEM_INFO) {
            return new HealthyItemItemInfo(View.inflate(context, R.layout.viewpager_healthy_itemitem_health_knowledge, null), context);
        }
        return null;
    }

    public Map<String, Object> getItemObject(int index) {
        return data.get(index);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.i("RecyclerViewAdapter", "Bind数据：" + holder.getClass().getSimpleName() + ",position=" + position);
        Map<String, Object> itemdata = data.get(position);
        int type = (int) itemdata.get("type");
        if (type == TYPE_BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            int function = (int) itemdata.get("function");
            if (function == FUNCTION_NORMALLOAD) {
                bannerViewHolder.loadData();
            } else if (function == FUNCTION_REFRESH) {
                bannerViewHolder.refresh();
                itemdata.put("function", FUNCTION_NORMALLOAD);
            }
//                bannerViewHolder.startPlay();
        } else {
            if (type == TYPE_ITEMGROUP) {
                HealthyItemGroup healthyItemGroup = (HealthyItemGroup) holder;
                String name = itemdata.get("name").toString();
                String smallname = itemdata.get("smallname").toString();
                healthyItemGroup.setName(name);
                healthyItemGroup.setSmallName(smallname);

            } else if (type == TYPE_ITEM_ASK) {
                HealthyItemItemAsk healthyItemItem = (HealthyItemItemAsk) holder;
                ArrayList<AskClassifyItem> item = (ArrayList<AskClassifyItem>) itemdata.get("data");
                healthyItemItem.setData(item);

            } else if (type == TYPE_ITEM_BOOKS) {
                HealthyItemItemBooks healthyItemItem = (HealthyItemItemBooks) holder;
                ArrayList<BookListItem> item = (ArrayList<BookListItem>) itemdata.get("data");
                healthyItemItem.setData(item);

            } else if (type == TYPE_ITEM_KNOWLEDGE) {
                HealthyItemItemKnowledge healthyItemItem = (HealthyItemItemKnowledge) holder;
                ArrayList<LoreListItem> item = (ArrayList<LoreListItem>) itemdata.get("data");
                healthyItemItem.setData(item);

            } else if (type == TYPE_ITEM_INFO) {
                HealthyItemItemInfo healthyItemItemInfo = (HealthyItemItemInfo) holder;
                ArrayList<InfoListItem> item = (ArrayList<InfoListItem>) itemdata.get("data");
                healthyItemItemInfo.setData(item);
            }
        }
    }

    /**
     * 追加数据
     *
     * @param itemdata
     */
    public void appendData(Map<String, Object> itemdata) {
        this.data.add(itemdata);
        notifyItemInserted(this.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
//        Log.i("RecyclerViewAdapter", "getItemViewType,position=" + position);
        Map<String, Object> itemdata = data.get(position);
        return (int) itemdata.get("type");
    }

    @Override
    public int getItemCount() {
//        Log.i("RecyclerViewAdapter", "getItemCount=" + data.size());
        return data.size();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_ITEMUPDATE) {
                updateData(msg.arg1, (Map<String, Object>) msg.obj);
            } else if (msg.what == WHAT_REFRESH_END) {
                if (refreshListener != null) {
                    refreshListener.onRefreshEnd();
                }
            } else if (msg.what == WHAT_START_AUTO_REFRESH) {
                generateOnlineData();//从网络获取数据
            } else if (msg.what == WHAT_ITEM_APPEND) {
                appendData((Map<String, Object>) msg.obj);
            } else if (msg.what == WHAT_ERROR) {
                Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    };

    public RefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    private RefreshListener refreshListener;

    public interface RefreshListener {
        void onRefreshEnd();
    }
}
