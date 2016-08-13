package android.microanswer.healthy;

import android.microanswer.healthy.BaseActivity;
import android.microanswer.healthy.bean.CookListItem;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 健康菜谱,详细信息
 * 由 Micro 创建于 2016/8/12.
 */

public class CookActivity extends BaseActivity {
    private CookListItem cookListItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        suitToolBar(R.id.activity_cook_toolbar);
        setToolBarBackEnable();

        cookListItem = (CookListItem) getIntent().getSerializableExtra("data");

        alertDialog("数据", cookListItem.toString()).show();

    }


    @Override
    protected boolean onHomeButtonClick() {
        onBackPressed();
        return super.onHomeButtonClick();
    }
}
