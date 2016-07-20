package android.microanswer.healthy;

import android.app.ProgressDialog;
import android.microanswer.healthy.bean.Friend;
import android.microanswer.healthy.bean.User;
import android.microanswer.healthy.tools.JavaBeanTools;
import android.os.Bundle;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

/**
 * 关注好友列表
 * 由 Micro 创建于 2016/7/20.
 */

public class FriendsActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        suitToolBar(R.id.activity_friends_toolbar);
        setToolBarBackEnable();
        listView = (ListView) findViewById(R.id.activity_friends_listview);
        runOnOtherThread(new BaseOtherThread() {
            ProgressDialog dialog = null;

            @Override
            void onOtherThreadRunEnd(Message msg) {
                List<Friend> friends = (List<Friend>) msg.obj;
                ArrayAdapter<Friend> adapter = new ArrayAdapter<Friend>(FriendsActivity.this, android.R.layout.simple_list_item_1, friends);
                listView.setAdapter(adapter);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public Map getTaskParams() {
                dialog = new ProgressDialog(FriendsActivity.this);
                dialog.setMessage("加载中...");
                dialog.show();
                return null;
            }

            @Override
            public Message run(Map params) {
                List<Friend> friends = JavaBeanTools.UserInterface.myHeart(30, 1, User.getUser().getAccount(), User.getUser().getAccess_token());
                Message msg = new Message();
                msg.obj = friends;
                return msg;
            }
        }, 1);
    }


    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return true;
    }
}
