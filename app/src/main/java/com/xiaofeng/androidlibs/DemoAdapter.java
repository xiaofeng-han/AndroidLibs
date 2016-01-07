package com.xiaofeng.androidlibs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xhan on 12/30/15.
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoItemHolder> {
	private final List<String> items;
	public DemoAdapter(String... items) {
		this.items = new LinkedList<>();
		this.items.addAll(Arrays.asList(items));
	}

	@Override
	public DemoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new DemoItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
	}

	@Override
	public void onBindViewHolder(DemoItemHolder holder, int position) {
		String item = items.get(position);
		holder.itemText.setText(item);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void append(String item) {
		items.add(item);
		notifyItemInserted(items.size());
	}

	public void insertAtBeginning(String item) {
		items.add(0, item);
		notifyItemInserted(0);
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
