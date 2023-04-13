package com.lilei.fitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lilei.fitness.R;

import org.w3c.dom.Text;

import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    public GoodsListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    private List<String> list;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GoodsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String s = this.list.get(position);
        if (holder instanceof GoodsViewHolder) {
            ((GoodsViewHolder) holder).textView.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class GoodsViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public GoodsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tv);
    }
}