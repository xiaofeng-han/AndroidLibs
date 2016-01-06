package com.xiaofeng.androidlibs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xhan on 12/30/15.
 */
public class DemoItemHolder extends RecyclerView.ViewHolder {
	public final TextView itemText;
	public DemoItemHolder(View itemView) {
		super(itemView);
		itemText = (TextView)itemView.findViewById(R.id.item_text);
	}
}
