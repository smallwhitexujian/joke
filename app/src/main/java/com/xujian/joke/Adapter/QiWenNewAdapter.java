package com.xujian.joke.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xj.frescolib.View.FrescoDrawee;
import com.xujian.joke.Activity.WebActivity;
import com.xujian.joke.Model.QiWenNew;
import com.xujian.joke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/29
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/29          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class QiWenNewAdapter extends RecyclerView.Adapter<QiWenNewAdapter.ListItemViewHolder> {
    private List<QiWenNew> mList  = new ArrayList<>();
    private Context mContext;

    public QiWenNewAdapter(Context context, List<QiWenNew> data) {
        if (data == null) {
            throw new IllegalArgumentException("model Data must not be null");
        }
        this.mContext = context;
        mList = data;
    }
    public void addData(List<QiWenNew> data) {
        mList = data;
        notifyDataSetChanged();
    }

    public void addOneData(QiWenNew funnyPic) {
        mList.add(mList.size(), funnyPic);
        notifyItemInserted(mList.size());
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_qiwen, parent, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, final int position) {
        holder.title.setText(mList.get(position).title);
        holder.context.setText(mList.get(position).description);
        holder.time.setText(mList.get(position).ctime);
        if (!mList.get(position).url.equals("")) {
            holder.frescoDrawee.setVisibility(View.VISIBLE);
            holder.frescoDrawee.setImageURI(mList.get(position).picUrl);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra(WebActivity.PARAM_KEY,mList.get(position).url);
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView context;
        TextView title;
        TextView time;
        FrescoDrawee frescoDrawee;
        LinearLayout root;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            context = (TextView) itemView.findViewById(R.id.Content);
            title = (TextView) itemView.findViewById(R.id.Title);
            time = (TextView) itemView.findViewById(R.id.time);
            root = (LinearLayout) itemView.findViewById(R.id.root);
            frescoDrawee = (FrescoDrawee) itemView.findViewById(R.id.frescoView);
        }
    }
}