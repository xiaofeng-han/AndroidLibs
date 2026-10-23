package com.xiaofeng.androidlibs;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Adapter for Demo item
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoViewHolder> {
    private List<String> items;

    private DemoAdapter() {
        this.items = new LinkedList<>();
    }

    public DemoAdapter(List<String> items) {
        this();
        this.items.addAll(items);
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DemoViewHolder holder, final int position) {
        holder.setTagText(items.get(position));
        //holder.tagSize.setClickable(false);
        holder.tagText.setClickable(false);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getBindingAdapterPosition();
                if(adapterPosition >= 0 && adapterPosition < items.size()) {
                    items.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adapterPosition = holder.getBindingAdapterPosition();
                if(adapterPosition >= 0 && adapterPosition < items.size()) {
                    List<String> newItems = DemoUtil.listWords();
                    items.addAll(adapterPosition, newItems);
                    notifyItemRangeInserted(adapterPosition, newItems.size());
                    return true;
                } else return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
