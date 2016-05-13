package com.xiaofeng.androidlibs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xiaofeng on 1/5/16.
 */
public class DemoViewHolder extends RecyclerView.ViewHolder {
	TextView tagText, tagSize;
	public DemoViewHolder(View itemView) {
		super(itemView);
		tagText = (TextView)itemView.findViewById(R.id.tag_text);
		tagSize = (TextView)itemView.findViewById(R.id.tag_count);
	}

	public void setTagText(String tag) {
		tagText.setText(tag);
		tagSize.setText("(" + tag.length() + ")");
	}
}
