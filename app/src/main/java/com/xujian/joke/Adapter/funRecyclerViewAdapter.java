package com.xujian.joke.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xj.utils.utils.DebugLogs;
import com.xujian.joke.Model.FunnyPic;
import com.xujian.joke.Activity.PicViewActivity;
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
        this.mContext = context;
        mList = data;
    }
    public void addData(List<FunnyPic.contentData> data) {
        mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_joke1, parent, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListItemViewHolder holder, final int position) {
        holder.title.setText(mList.get(position).updatetime);
        holder.context.setText(mList.get(position).content);
        DebugLogs.d("------>" + mList.get(position).url);
        if (!mList.get(position).url.equals("")) {
            holder.frescoDrawee.setVisibility(View.VISIBLE);
            Glide.with(holder.frescoDrawee.getContext()).load(mList.get(position).url).placeholder(R.drawable.ic_launcher).into(holder.frescoDrawee);
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
        ImageView frescoDrawee;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            context = (TextView) itemView.findViewById(R.id.jokeContent);
            title = (TextView) itemView.findViewById(R.id.jokeTitle);
            frescoDrawee = (ImageView) itemView.findViewById(R.id.frescoView1231231);
        }
    }
}
