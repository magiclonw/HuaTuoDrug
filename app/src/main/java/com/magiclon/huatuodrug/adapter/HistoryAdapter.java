package com.magiclon.huatuodrug.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.magiclon.huatuodrug.R;

import java.util.List;

/**
 * 作者：MagicLon
 * 时间：2017/7/18
 * 邮箱：1348149485@qq.com
 * 描述：
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final List<String> mList;

    public HistoryAdapter(List<String> list, Context context) {
        Context mContext = context;
        this.mList = list;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnDeleteLister onDeleteLister;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnDeleteLister(OnDeleteLister onDeleteLister) {
        this.onDeleteLister = onDeleteLister;
    }

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnDeleteLister {
        void onDeleteOne(String name);

        void onDeleteAll();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        final ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(mList.get(position));
        holder.item_history_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, position);
            }
        });
        holder.item_history_delall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteLister.onDeleteAll();
            }
        });
        holder.item_history_delone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteLister.onDeleteOne(mList.get(position));
            }
        });
        if (position == mList.size() - 1) {
            holder.item_history_delall.setVisibility(View.VISIBLE);
        } else {
            holder.item_history_delall.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final LinearLayout item_history_info;
        private final LinearLayout item_history_delall;
        private final ImageView item_history_delone;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_history_info);
            item_history_info = itemView.findViewById(R.id.item_history_info);
            item_history_delall = itemView.findViewById(R.id.item_history_delall);
            item_history_delone = itemView.findViewById(R.id.item_history_delone);
        }
    }
}
