package android.microanswer.healthy.adapter;

import android.content.Context;
import android.microanswer.healthy.R;
import android.microanswer.healthy.bean.FoodListItem;
import android.microanswer.healthy.fragment.FoodFragment;
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

public class FoodRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SparseArray<List<FoodListItem>> data;
    private Context context;
    private int currentClassify = -1;

    public FoodRecyclerViewAdapter(Context context) {
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
     * 获取当前显示的分类
     *
     * @return
     */
    public int getCurrentClassify() {
        return currentClassify;
    }

    /**
     * 设置对应菜谱分类的数据
     *
     * @param classify
     * @param classifyData
     */
    public void setClassifyData(int classify, List<FoodListItem> classifyData) {
        this.data.put(classify, classifyData);
        if (this.currentClassify == classify) {
            notifyDataSetChanged();
        }
    }

    /**
     * 追加对应分类数据
     *
     * @param classify
     * @param data
     */
    public void appendClassifyData(int classify, List<FoodListItem> data) {
        List<FoodListItem> cookListItems = this.data.get(classify);
        int oldSize = cookListItems.size();
        cookListItems.addAll(data);
        if (classify == currentClassify) {
            notifyItemRangeInserted(oldSize + 1, data.size());
        }
    }

    /**
     * 获取对应分类的数据
     *
     * @param classify
     * @return
     */
    public List<FoodListItem> getClassifyData(int classify) {
        return this.data.get(classify);
    }

    /**
     * 获取当前显示了几页
     * @return
     */
    public int getCurrentClassifyPage() {
        return getClassifyData(currentClassify).size() / FoodFragment.PAGE_COUNT;
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
            FoodListItem cookListItem = data.get(currentClassify).get(position);
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
