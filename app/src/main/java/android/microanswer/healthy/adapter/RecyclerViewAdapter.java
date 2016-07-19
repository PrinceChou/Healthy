package android.microanswer.healthy.adapter;

import android.content.Context;
import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.AskClassifyItem;
import android.microanswer.healthy.bean.BookListItem;
import android.microanswer.healthy.bean.InfoListItem;
import android.microanswer.healthy.bean.LoreListItem;
import android.microanswer.healthy.database.DataManager;
import android.microanswer.healthy.tools.BaseTools;
import android.microanswer.healthy.tools.JavaBeanTools;
import android.microanswer.healthy.viewbean.HealthyItemGroup;
import android.microanswer.healthy.viewbean.HealthyItemItemAsk;
import android.microanswer.healthy.viewbean.HealthyItemItemBooks;
import android.microanswer.healthy.viewbean.HealthyItemItemInfo;
import android.microanswer.healthy.viewbean.HealthyItemItemKnowledge;
import android.microanswer.healthy.viewbean.SmartBannerViewHolder;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 由 Micro 创建于 2016/6/30.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter implements HealthyItemItemAsk.OnHealItemItemAskClickListener, HealthyItemItemBooks.OnHealthyItemItemBooksClickListener, HealthyItemItemKnowledge.OnHealthyItemKnowledgeClickListener, HealthyItemItemInfo.OnHealthyItemItemInfoClickListener, SmartBannerViewHolder.OnSmartBannerItemClickListener {
    public static final int TYPE_BANNER = 1;
    public static final int TYPE_ITEMGROUP = 2;
    public static final int TYPE_ITEM_KNOWLEDGE = 3;
    public static final int TYPE_ITEM_BOOKS = 4;
    public static final int TYPE_ITEM_ASK = 5;
    public static final int TYPE_ITEM_INFO = 6;


//    public static final int FUNCTION_NORMALLOAD = 1;
//    public static final int FUNCTION_REFRESH = 2;

    private static final int WHAT_ITEMUPDATE = 1;
    private static final int WHAT_REFRESH_END = 2;
    //    private static final int WHAT_START_AUTO_REFRESH = 3;
    private static final int WHAT_ITEM_APPEND = 4;
    private static final int WHAT_ERROR = 5;

    private Context context;

    private DataManager dataManager;

    private OnItemClickListener onItemClickListener;


    /**
     * 数据格式：<br/>
     * ArrayList<Map<String,Object>><br/>
     * Map<String,Object><br/>
     * ["type":"type-value"]<br/>
     * ["data":"item-data"]<br/>
     */
    private ArrayList<Map<String, Object>> data;

    public RecyclerViewAdapter(Context ccontext) {
        this.context = ccontext;
        dataManager = new DataManager(context);
        data = generateDisOnlineData();
    }

    /**
     * 数据生成方法，用于没有加载完成数据的时候显示
     *
     * @return
     */
    private ArrayList<Map<String, Object>> generateDisOnlineData() {


        ArrayList<Map<String, Object>> datalist = new ArrayList<>();

        Random rd = new Random();

        Map<String, Object> banner = new HashMap<>();
        banner.put("type", TYPE_BANNER);

        int inclassifyid = rd.nextInt(7) + 1;
        ArrayList<InfoListItem> infoListItems = dataManager.getInfoListItems(10, 1, inclassifyid);
        Log.i("从数据库获取到的", "健康咨询=" + infoListItems.toString());

        banner.put("data", infoListItems);
        datalist.add(banner);
        //构建Banner

        Map<String, Object> group1 = new HashMap<>();
        group1.put("type", TYPE_ITEMGROUP);
        group1.put("name", "健康知识");
        group1.put("smallname", "Health Knowledge");
        datalist.add(group1);
        //构建健康知识标题

        ArrayList<LoreListItem> loreListItems = dataManager.getLoreListItems(4, 1, rd.nextInt(7) + 1);
        Log.i("从数据库获取到的", "健康知识=" + loreListItems.toString());
        if (loreListItems == null || loreListItems.size() != 4) {
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
        } else {
            for (int i = 0; i < 2; i++) {
                Map<String, Object> knowledgedata = new HashMap<>();
                knowledgedata.put("type", TYPE_ITEM_KNOWLEDGE);
                knowledgedata.put("data", loreListItems.subList(i * 2, (i * 2) + 2));
                datalist.add(knowledgedata);
            }
        }

        Map<String, Object> group2 = new HashMap<>();
        group2.put("type", TYPE_ITEMGROUP);
        group2.put("name", "健康问答");
        group2.put("smallname", "Health Question & Answer");
        datalist.add(group2);
        //构建健康问答标题

        ArrayList<AskClassifyItem> askClassifyItems = dataManager.getAskClassifyItems();
        if (askClassifyItems == null || askClassifyItems.size() < 12) {
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
            }
        } else {//构建健康问答数据
            for (int i = 0; i < 3; i++) {
                Map<String, Object> askdata = new HashMap<>();
                askdata.put("type", TYPE_ITEM_ASK);
                List<AskClassifyItem> item_in_item = askClassifyItems.subList((i * 4), (i * 4) + 4);
                askdata.put("data", item_in_item);
                datalist.add(askdata);
            }
        }

        Map<String, Object> group3 = new HashMap<>();
        group3.put("type", TYPE_ITEMGROUP);
        group3.put("name", "健康图书");
        group3.put("smallname", "Health Books");
        datalist.add(group3);
        //构建健康问答标题

        int bookclassify = rd.nextInt(10) + 1;
        ArrayList<BookListItem> bookListItems = dataManager.getBookListItems(9, 1, bookclassify);
        if (bookListItems == null || bookListItems.size() != 9) {
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
            }
        } else {
            for (int m = 0; m < 3; m++) {
                Map<String, Object> bookdata = new HashMap<>();
                bookdata.put("type", TYPE_ITEM_BOOKS);
                List<BookListItem> item_in_item = bookListItems.subList((m * 3), (m * 3) + 3);
                bookdata.put("data", item_in_item);
                datalist.add(bookdata);
            }
        }//构建健康问答数据

        Map<String, Object> group4 = new HashMap<>();
        group4.put("type", TYPE_ITEMGROUP);
        group4.put("name", "更多资讯");
        group4.put("smallname", "More Healthy Info");
        datalist.add(group4);
        //构建更多资讯标题


        ArrayList<InfoListItem> infoListItems1 = dataManager.getInfoListItems(10, 2, inclassifyid);
        if (infoListItems1 == null || infoListItems1.size() != 10) {
            for (int i = 0; i < 5; i++) {
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
            }
        } else {//构建更多数据
            for (int i = 0; i < 5; i++) {
                Map<String, Object> Infolistdata = new HashMap<>();
                Infolistdata.put("type", TYPE_ITEM_INFO);
                List<InfoListItem> item_in_item = infoListItems1.subList((i * 2), (i * 2) + 2);
                Infolistdata.put("data", item_in_item);
                datalist.add(Infolistdata);
            }
        }
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
                Log.i("从数据库", "获取到的最后一个数据是:" + itemObject.toString());
                List<InfoListItem> itemdatas = (List<InfoListItem>) itemObject.get("data");
                if (itemdatas == null || itemdatas.size() < 1) {
                    isappending = false;
                    return;
                }
                InfoListItem lastinfo = itemdatas.get(1);
                int startindex = getItemCount() + 1;
                int lastinfoclass = lastinfo.getInfoclass();
                List<InfoListItem> infoListData = JavaBeanTools.Info.getInfoListData(infoPage, 10, lastinfoclass);
                if (infoListData != null) {
                    infoPage++;
                    Log.i("从数据库", "从网络获取到的资讯写入数据库" + dataManager.putInfoListItems(infoListData) + "条");
                    for (int i = 0; i < 5; i++) {
                        Map<String, Object> infoitemdata = new HashMap<>();
                        infoitemdata.put("type", TYPE_ITEM_INFO);
                        infoitemdata.put("data", infoListData.subList((i * 2), (i * 2) + 2));
                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_ITEM_APPEND;
                        msg.obj = infoitemdata;
                        msg.sendToTarget();
                    }//构建更多资讯数据
                } else {
                    Message msge = Message.obtain(handler);
                    msge.what = WHAT_ERROR;
                    msge.obj = "你的网络好像不怎么好！";
                    msge.sendToTarget();
                    isappending = false;

                    infoListData = dataManager.getInfoListItems(10, infoPage, lastinfoclass);
                    if (infoListData != null && infoListData.size() == 10) {
                        infoPage++;
                        for (int i = 0; i < 5; i++) {
                            Map<String, Object> infoitemdata = new HashMap<>();
                            infoitemdata.put("type", TYPE_ITEM_INFO);
                            infoitemdata.put("data", infoListData.subList((i * 2), (i * 2) + 2));
                            Message msg = handler.obtainMessage();
                            msg.what = WHAT_ITEM_APPEND;
                            msg.obj = infoitemdata;
                            msg.sendToTarget();
                        }//构建更多资讯数据
                    } else {


                    }

                }
               /* for (int i = 0; i < 5; i++) {
                    Message msg = Message.obtain(handler);
                    msg.what = WHAT_ITEM_APPEND;
                    Map<String, Object> item = new HashMap<String, Object>();
                    item.put("type", TYPE_ITEM_INFO);
                    try {
                        List<InfoListItem> data = JavaBeanTools.Info.getInfoListData(++infoPage, 2, lastinfoclass);
                        item.put("data", data);
                    } catch (Exception e) {
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
                }*/
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
                try {
                    final Random rd = new Random();

                    Map<String, Object> banner = new HashMap<>();
                    banner.put("type", TYPE_BANNER);
                    int classifyId = rd.nextInt(7);

                    List<InfoListItem> infoListData1 = JavaBeanTools.Info.getInfoListData(1, 10, classifyId);
                    if (infoListData1 != null) {
                        dataManager.putInfoListItems(infoListData1);
                        banner.put("data", infoListData1);
                        Message msg0 = handler.obtainMessage();
                        msg0.what = WHAT_ITEMUPDATE;
                        msg0.obj = banner;
                        msg0.arg1 = 0;
                        handler.sendMessage(msg0);
                    }
                    //刷新banner数据


                    int loreClassID = rd.nextInt(7);
                    for (int i = 0; i < 2; i++) {
                        List<LoreListItem> loreListData = JavaBeanTools.Lore.getLoreListData(1 + i, 2, loreClassID);
                        if (loreListData != null) {
                            dataManager.putLoreListItems(loreListData);
                            HashMap<String, Object> items = new HashMap<String, Object>();
                            items.put("type", TYPE_ITEM_KNOWLEDGE);
                            items.put("data", loreListData);
                            Message msg = handler.obtainMessage();
                            msg.what = WHAT_ITEMUPDATE;
                            msg.obj = items;
                            msg.arg1 = (i + 2);
                            handler.sendMessage(msg);
                        }
                    }//请求健康知识数据

                    List<AskClassifyItem> askClassifyData = JavaBeanTools.Ask.getAskClassifyData();

                    if (askClassifyData != null) {
                        int i1 = dataManager.putAskClassifyItems(askClassifyData);
                        Log.i("从数据库","从网络获取问答分类后存入数据库成功了"+i1+"条");
                        for (int i = 0; i < 3; i++) {
                            Map<String, Object> askdata = new HashMap<>();
                            askdata.put("type", TYPE_ITEM_ASK);
                            List<AskClassifyItem> item_in_item = askClassifyData.subList((i * 4), (i * 4) + 4);
                            askdata.put("data", item_in_item);
                            Message msg = handler.obtainMessage();
                            msg.what = WHAT_ITEMUPDATE;
                            msg.arg1 = (i + 5);
                            msg.obj = askdata;
                            handler.sendMessage(msg);
                        }
                    }//请求健康问答数据


                    int bookclassify = rd.nextInt(10) + 1;
                    List<BookListItem> itembookData = JavaBeanTools.Book.getBookList(1, 9, bookclassify);
                    if (itembookData != null) {
                        Log.i("从数据库", "从网络获取图书列表并向数据库写入了" + dataManager.putBookListItems(itembookData) + "条数据");
                        for (int m = 0; m < 3; m++) {
                            Map<String, Object> bookdata = new HashMap<>();
                            bookdata.put("type", TYPE_ITEM_BOOKS);
                            List<BookListItem> item_in_item = itembookData.subList((m * 3), (m * 3) + 3);
                            bookdata.put("data", item_in_item);
                            Message msg = handler.obtainMessage();
                            msg.what = WHAT_ITEMUPDATE;
                            msg.arg1 = (m + 9);
                            msg.obj = bookdata;
                            handler.sendMessage(msg);
                        }
                    }

                    /*int id = rd.nextInt(7);
                    for (int j = 0; j < 3; j++) {
                        List<BookListItem> itembookData = JavaBeanTools.Book.getBookList((1 + j), 3, id);
                        Map<String, Object> items = new HashMap<String, Object>();
                        items.put("type", TYPE_ITEM_BOOKS);
                        items.put("data", itembookData);
                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_ITEMUPDATE;
                        msg.arg1 = (j + 9);
                        msg.obj = items;
                        handler.sendMessage(msg);
                    }//请求健康图书数据*/


                    List<InfoListItem> infoListData = JavaBeanTools.Info.getInfoListData(2, 10, classifyId);
                    if (infoListData != null) {
                        Log.i("从数据库", "从网络获取到的资讯写入数据库" + dataManager.putInfoListItems(infoListData) + "条");
                        for (int i = 0; i < 5; i++) {
                            Map<String, Object> infoitemdata = new HashMap<>();
                            infoitemdata.put("type", TYPE_ITEM_INFO);
                            infoitemdata.put("data", infoListData.subList((i * 2), (i * 2) + 2));
                            Message msg = handler.obtainMessage();
                            msg.what = WHAT_ITEMUPDATE;
                            msg.arg1 = (i + 13);
                            msg.obj = infoitemdata;
                            msg.sendToTarget();
                        }//构建更多资讯数据
                        infoPage = 3;
                    }
                    /*
                    for (int i = 0; i < 3; i++) {
                        Map<String, Object> infoitemdata = new HashMap<>();
                        infoPage = (1 + i);
                        List<InfoListItem> infoListData = JavaBeanTools.Info.getInfoListData(infoPage, 2, rd.nextInt(7) + 1);
                        infoitemdata.put("type", TYPE_ITEM_INFO);
                        infoitemdata.put("data", infoListData);
                        Message msg = handler.obtainMessage();
                        msg.what = WHAT_ITEMUPDATE;
                        msg.arg1 = (i + 13);
                        msg.obj = infoitemdata;
                        msg.sendToTarget();
                    }//构建更多资讯数据*/


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
        Map<String, Object> stringObjectMap = this.data.get(index);
        Set<String> strings = stringObjectMap.keySet();
        for (String key : strings) {
            stringObjectMap.put(key, dataitem.get(key));
        }
//        this.data.set(index, dataitem);
        this.notifyItemChanged(index);
    }

    private SmartBannerViewHolder smartBannerViewHolder;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i("RecyclerViewAdapter", "onCreateViewHolder,parent=" + parent.getClass().getSimpleName() + ",viewType=" + viewType);
        if (viewType == TYPE_BANNER) {
            if (smartBannerViewHolder == null) {
                View bannerview = View.inflate(context, R.layout.healthy_banner, null);
//            return new BannerViewHolder(bannerview, context, parentFragment);
                smartBannerViewHolder = new SmartBannerViewHolder(bannerview);
            }
            return smartBannerViewHolder;
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

//    @Override
//    public void onViewRecycled(RecyclerView.ViewHolder holder) {
//        super.onViewRecycled(holder);
//        if (holder != null && holder == smartBannerViewHolder) {
//            smartBannerViewHolder.stopTurning();
//        }
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.i("RecyclerViewAdapter", "Bind数据：" + holder.getClass().getSimpleName() + ",position=" + position);
        Map<String, Object> itemdata = data.get(position);
        int type = (int) itemdata.get("type");
        if (type == TYPE_BANNER) {
            SmartBannerViewHolder bannerViewHolder = (SmartBannerViewHolder) holder;
            bannerViewHolder.setOnSmartBannerItemClickListener(this);
            bannerViewHolder.setData((List<InfoListItem>) itemdata.get("data"));
        } else {
            if (type == TYPE_ITEMGROUP) {
                HealthyItemGroup healthyItemGroup = (HealthyItemGroup) holder;
                String name = itemdata.get("name").toString();
                String smallname = itemdata.get("smallname").toString();
                healthyItemGroup.setName(name);
                healthyItemGroup.setSmallName(smallname);

            } else if (type == TYPE_ITEM_ASK) {
                HealthyItemItemAsk healthyItemItem = (HealthyItemItemAsk) holder;
                healthyItemItem.setOnHealItemItemAskClickListener(this);
                List<AskClassifyItem> item = (List<AskClassifyItem>) itemdata.get("data");
                if (item != null)
                    healthyItemItem.setData(item);

            } else if (type == TYPE_ITEM_BOOKS) {
                HealthyItemItemBooks healthyItemItem = (HealthyItemItemBooks) holder;
                healthyItemItem.setOnHealthyItemItemBooksClickListener(this);
                List<BookListItem> item = (List<BookListItem>) itemdata.get("data");
                if (item != null)
                    healthyItemItem.setData(item);

            } else if (type == TYPE_ITEM_KNOWLEDGE) {
                HealthyItemItemKnowledge healthyItemItem = (HealthyItemItemKnowledge) holder;
                healthyItemItem.setOnHealthyItemKnowledgeClickListener(this);
                List<LoreListItem> item = (List<LoreListItem>) itemdata.get("data");
                if (item != null)
                    healthyItemItem.setData(item);

            } else if (type == TYPE_ITEM_INFO) {
                HealthyItemItemInfo healthyItemItemInfo = (HealthyItemItemInfo) holder;
                healthyItemItemInfo.setOnHealthyItemItemInfoClickListener(this);
                List<InfoListItem> item = (List<InfoListItem>) itemdata.get("data");
                if (item != null)
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

    /**
     * 健康问答点击
     *
     * @param askClassifyItem
     */
    @Override
    public void onClick(AskClassifyItem askClassifyItem) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick(askClassifyItem);
        }
    }

    /**
     * 健康图书点击
     *
     * @param item
     */
    @Override
    public void onClick(BookListItem item) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick(item);
        }
    }

    /**
     * 健康知识点击
     *
     * @param item
     */
    @Override
    public void onclick(LoreListItem item) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick(item);
        }
    }

    /**
     * 健康咨询点击
     *
     * @param item
     */
    @Override
    public void onclick(InfoListItem item) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick(item);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(InfoListItem item) {
        this.onclick(item);
    }

    public interface RefreshListener {
        void onRefreshEnd();
    }

    public interface OnItemClickListener {
        void onClick(Object item);
    }
}
