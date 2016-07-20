package android.microanswer.healthy;

import android.os.Bundle;

/**
 * 关注好友列表
 * 由 Micro 创建于 2016/7/20.
 */

public class FriendsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        suitToolBar(R.id.activity_friends_toolbar);
        setToolBarBackEnable();
    }

    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return true;
    }
}
