package com.xujian.joke.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xj.frescolib.View.FrescoDrawee;
import com.xj.utils.utils.DebugLogs;
import com.xujian.joke.Model.FunnyPic;
import com.xujian.joke.PicViewActivity;
import com.xujian.joke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/17
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/17          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class funRecyclerViewAdapter extends RecyclerView.Adapter<funRecyclerViewAdapter.ListItemViewHolder> {
    private List<FunnyPic.contentData> mList  = new ArrayList<>();
    private Context mContext;

    public funRecyclerViewAdapter(Context context, List<FunnyPic.contentData> data) {
        if (data == null) {
            throw new IllegalArgumentException("model Data must not be null");
        }
        this.mContext = context;
        mList = data;
    }
    public void addData(List<FunnyPic.contentData> data) {
        mList = data;
        notifyDataSetChanged();
    }

    public void addOneData(FunnyPic.contentData funnyPic) {
        mList.add(mList.size(), funnyPic);
        notifyItemInserted(mList.size());
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_joke, parent, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, final int position) {
        holder.title.setText(mList.get(position).updatetime);
        holder.context.setText(mList.get(position).content);
        DebugLogs.d("------>" + mList.get(position).url);
        if (!mList.get(position).url.equals("")) {
            holder.frescoDrawee.setVisibility(View.VISIBLE);
            holder.frescoDrawee.setImageURI(mList.get(position).url);
            holder.frescoDrawee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PicViewActivity.class);
                    intent.putExtra("url",mList.get(position).url);
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
        FrescoDrawee frescoDrawee;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            context = (TextView) itemView.findViewById(R.id.jokeContent);
            title = (TextView) itemView.findViewById(R.id.jokeTitle);
            frescoDrawee = (FrescoDrawee) itemView.findViewById(R.id.frescoView);
        }
    }
}
