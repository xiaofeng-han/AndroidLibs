package com.xiaofeng.androidlibs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Demo item
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoViewHolder> {
	List<String> items;

	public DemoAdapter(List<String> items) {
		this.items = new ArrayList<>(items.size());
		this.items.addAll(items);
	}

	@Override
	public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new DemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_tag, parent, false));
	}

	@Override
	public void onBindViewHolder(final DemoViewHolder holder, final int position) {
		holder.setTagText(items.get(position));
		holder.tagSize.setClickable(false);
		holder.tagText.setClickable(false);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int adapterPosition = holder.getAdapterPosition();
				items.remove(adapterPosition);
				notifyItemRemoved(adapterPosition);
			}
		});
		holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				int adapterPosition = holder.getAdapterPosition();
				List<String> newItems = DemoUtil.generate(1, 3, 14, 1, true);
				items.addAll(adapterPosition, newItems);
				notifyItemRangeInserted(adapterPosition, newItems.size());
				return true;
			}
		});
	}

	@Override
	public int getItemCount() {
		return items.size();
	}
}
