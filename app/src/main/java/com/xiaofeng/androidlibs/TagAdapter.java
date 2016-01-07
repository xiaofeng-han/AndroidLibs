package com.xiaofeng.androidlibs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaofeng on 1/5/16.
 */
public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {
	List<String> items;

	public TagAdapter(List<String> items) {
		this.items = new ArrayList<>(items.size());
		this.items.addAll(items);
	}

	@Override
	public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new TagViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_tag, parent, false));
	}

	@Override
	public void onBindViewHolder(TagViewHolder holder, int position) {
		holder.setTagText(items.get(position));
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void move (int from, int to) {
		if (from > to) {
			String item = items.remove(from);
			items.add(to, item);
		} else {
			items.add(to, items.get(from));
			items.remove(from);
		}
		notifyItemMoved(from, to);
	}
}
