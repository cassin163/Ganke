package com.github.koooe.ganke.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koooe.ganke.R;
import com.github.koooe.ganke.bean.DayData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by <b>kassadin@foxmail.com</b> on 15/8/30 00:02
 */
public class FuliAdapter extends RecyclerView.Adapter<FuliAdapter.FuliViewHolder> {

    List<DayData> datas;

    LayoutInflater inflater;
    OnItemClickListener onItemClickListener;
    ImageLoader imageLoader = ImageLoader.getInstance();


    public FuliAdapter(@NonNull Context context, @NonNull List<DayData> datas) {
        inflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override

    public FuliViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FuliViewHolder(inflater.inflate(R.layout.item_fuli, parent, false));
    }

    @Override
    public void onBindViewHolder(FuliViewHolder holder, int position) {
        final DayData dayData = datas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(dayData);
                }
            }
        });

        imageLoader.displayImage(dayData.getUrl(), holder.imageView);
        holder.textView.setText(dayData.getWho());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class FuliViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imageView)
        ImageView imageView;

        @Bind(R.id.textView)
        TextView textView;

        public FuliViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(DayData data);
    }
}
