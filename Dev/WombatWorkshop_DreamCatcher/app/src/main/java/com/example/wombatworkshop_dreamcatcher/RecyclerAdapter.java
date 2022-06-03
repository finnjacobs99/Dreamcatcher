package com.example.wombatworkshop_dreamcatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<BucketItem> itemList;
    private OnItemListener mOnItemListener;

    public RecyclerAdapter(ArrayList<BucketItem> itemList, OnItemListener onItemListener){
        this.itemList = itemList;
        this.mOnItemListener = onItemListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemName;

        OnItemListener onItemListener;

        public MyViewHolder(final View view, OnItemListener onItemListener){
            super(view);

            this.onItemListener = onItemListener;
            view.setOnClickListener(this);

            itemName = view.findViewById(R.id.ItemName);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bucket_items, parent, false);
        return new MyViewHolder(itemView, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String name = itemList.get(position).getItemName();
        holder.itemName.setText(name);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}
