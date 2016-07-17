package android.microanswer.healthy.adapter;

import android.content.Context;
import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.CookListItem;
import android.microanswer.healthy.viewbean.CookItemHolder;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * 健康才欧里面的RecyclerView的适配器
 * 由 Micro 创建于 2016/7/18.
 */

public class CookRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SparseArray<List<CookListItem>> data;
    private Context context;
    private int currentClassify = -1;

    public CookRecyclerViewAdapter(Context context) {
        this.context = context;
        this.data = new SparseArray<>();
    }

    public void setCurrentClassify(int currentClassify) {
        if (this.currentClassify == currentClassify) {
            return;
        }
        this.currentClassify = currentClassify;
        notifyDataSetChanged();
    }

    /**
     * 设置对应菜谱分类的数据
     *
     * @param classify
     * @param classifyData
     */
    public void setClassifyData(int classify, List<CookListItem> classifyData) {
        this.data.put(classify, classifyData);
        if (this.currentClassify == classify) {
            notifyDataSetChanged();
        }
    }

    /**
     * 获取对应分类的数据
     *
     * @param classify
     * @return
     */
    public List<CookListItem> getClassifyData(int classify) {
        return this.data.get(classify);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = View.inflate(context, R.layout.view_cook_item, null);
        return new CookItemHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CookItemHolder) {
            CookItemHolder cookItemHolder = (CookItemHolder) holder;
            CookListItem cookListItem = data.get(currentClassify).get(position);
            cookItemHolder.setImg(cookListItem.getImg());
            cookItemHolder.setTitle(cookListItem.getName());
        }

    }

    @Override
    public int getItemCount() {
        if (currentClassify == -1 || data.get(currentClassify) == null) {
            Toast.makeText(context, "No Data To Show With CookClassify " + currentClassify + " !", Toast.LENGTH_SHORT).show();
            return 0;
        }

        return data.get(currentClassify).size();
    }


}
