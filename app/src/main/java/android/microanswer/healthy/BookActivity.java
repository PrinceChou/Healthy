package android.microanswer.healthy;

import android.graphics.Color;
import android.microanswer.healthy.bean.BookListItem;
import android.microanswer.healthy.tools.InternetServiceTool;
import android.microanswer.healthy.view.BookPageDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 由 Micro 创建于 2016/8/9.
 */

public class BookActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "BookActivity";

    private BookListItem bookListItem;

    private TextView title;
    private LinearLayout activity_book_mulucontent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        suitToolBar(R.id.activity_book_toolbar);
        setToolBarBackEnable();

        bookListItem = (BookListItem) getIntent().getSerializableExtra("data");

        title = (TextView) findViewById(R.id.activity_book_title);
        activity_book_mulucontent = (LinearLayout) findViewById(R.id.activity_book_mulucontent);
        title.setText(bookListItem.getName());
        requestBook();
    }


    private void onBookGeted() {
        title.setText(bookListItem.getName());
        for (int i = 0; i < bookListItem.getList().size(); i++) {
            BookListItem.BookPage bkg = bookListItem.getList().get(i);
            TextView tv = new TextView(this);
            tv.setTag(i);
            tv.setOnClickListener(this);
            tv.setPadding(title.getPaddingLeft(), title.getPaddingBottom(), title.getPaddingRight(), title.getPaddingBottom());
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText(bkg.getTitle());
            tv.setClickable(true);
            tv.setBackgroundDrawable(getDrawable(android.support.v7.appcompat.R.drawable.abc_list_selector_holo_light));
            tv.setTextColor(Color.BLACK);
            activity_book_mulucontent.addView(tv);
        }
    }


    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return super.onHomeButtonClick();
    }

    @Override
    protected void onDestroy() {
        bookListItem = null;
        shutDownAllOtherThread();
        super.onDestroy();
    }

    /**
     * 该方法用于请求详细图书数据
     */
    private void requestBook() {
        runOnOtherThread(new BaseOtherThread() {
            @Override
            void onOtherThreadRunEnd(Message msg) {

                if (msg != null) {
                    bookListItem = (BookListItem) msg.obj;
                    onBookGeted();
                } else {
                    //TODO 加载失败
                    toast("加载失败", POSOTION_BOTTOM);
                }

            }

            @Override
            public Map getTaskParams() {
                HashMap<String, Integer> integerHashMap = new HashMap<String, Integer>();
                integerHashMap.put("id", bookListItem.getId());
                return integerHashMap;
            }

            @Override
            public Message run(Map params) {
                String url = "http://www.tngou.net/api/book/show?id=" + params.get("id");
                try {
                    String request = InternetServiceTool.request(url);
                    JSONObject jsonObject = JSON.parseObject(request);
                    if (jsonObject.getBooleanValue("status")) {
                        BookListItem bookListItem = JSON.parseObject(request, BookListItem.class);

                        Collections.sort(bookListItem.getList());

                        Message msg = new Message();
                        msg.obj = bookListItem;
                        return msg;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, 49302);
    }


    @Override
    public void onClick(View view) {
        if (view instanceof TextView && view.getTag() != null) {
            new BookPageDialog(this, bookListItem, (int) view.getTag()).show();
        }
    }
}
