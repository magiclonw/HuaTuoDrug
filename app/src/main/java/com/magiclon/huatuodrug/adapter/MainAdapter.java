package com.magiclon.huatuodrug.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magiclon.huatuodrug.R;
import com.magiclon.huatuodrug.model.CommonDrugBean;

import java.util.List;

/**
 * 作者：MagicLon
 * 时间：2017/7/18
 * 邮箱：1348149485@qq.com
 * 描述：
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private final List<CommonDrugBean> mList;

    public MainAdapter(List<CommonDrugBean> list, Context context) {
        Context mContext = context;
        this.mList = list;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view1,View view2, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(vh.tv_title,vh.tv_content, vh.getLayoutPosition());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_title.setText(mList.get(position).getTitle());
        holder.tv_content.setText(mList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_title;
        private final TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_mainitem_title);
            tv_content = itemView.findViewById(R.id.tv_mainitem_content);

        }
    }
}
