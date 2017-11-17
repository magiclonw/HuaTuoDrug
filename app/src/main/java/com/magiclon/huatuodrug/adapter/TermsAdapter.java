package com.magiclon.huatuodrug.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.magiclon.huatuodrug.R;
import com.magiclon.huatuodrug.model.TermsBean;

import java.util.List;

/**
 * 作者：MagicLon
 * 时间：2017/7/18
 * 邮箱：1348149485@qq.com
 * 描述：
 */
public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {
    private final List<TermsBean> mList;
    private String type = "1";

    public TermsAdapter(List<TermsBean> list, Context context, String type) {
        Context mContext = context;
        this.mList = list;
        this.type = type;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_terms, parent, false);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, vh.getLayoutPosition());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_title.setText(mList.get(position).getPname());
        if(type.equals("2")){
            holder.iv_terms_pic.setImageResource(R.mipmap.drug_green);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_title;
        private final ImageView iv_terms_pic;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_terms_title);
            iv_terms_pic = itemView.findViewById(R.id.iv_terms_pic);

        }
    }
}
