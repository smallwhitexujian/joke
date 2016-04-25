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
import com.xujian.joke.Model.JokeModel;
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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListItemViewHolder> {
    private List<JokeModel> mList  = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<JokeModel> data) {
        if (data == null) {
            throw new IllegalArgumentException("model Data must not be null");
        }
        this.mContext = context;
        mList = data;
    }
    public void addData(List<JokeModel> data) {
        mList = data;
        notifyDataSetChanged();
    }

    public void addOneData(JokeModel jokeModel) {
        mList.add(mList.size(), jokeModel);
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
        holder.title.setText(mList.get(position).author);
        holder.context.setText(mList.get(position).content);
        DebugLogs.d("------>" + mList.get(position).picUrl);
        if (!mList.get(position).picUrl.equals("")) {
            holder.frescoDrawee.setVisibility(View.VISIBLE);
            holder.frescoDrawee.setImageURI(mList.get(position).picUrl);
            holder.frescoDrawee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PicViewActivity.class);
                    intent.putExtra("url",mList.get(position).picUrl);
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
        DebugLogs.d(mList.size()+"");
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
