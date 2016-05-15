package com.xiaofeng.androidlibs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class DemoViewHolder extends RecyclerView.ViewHolder {
	boolean showMeta = false;
	TextView tagText, tagSize;
	public DemoViewHolder(View itemView) {
		super(itemView);
		tagText = (TextView)itemView.findViewById(R.id.tag_text);
		tagSize = (TextView)itemView.findViewById(R.id.tag_count);
	}

	public void setTagText(String tag) {
		tagText.setText(tag);
		if (showMeta) {
			String[] lines = tag.split("\n");
			StringBuilder sb = new StringBuilder();
			sb.append("(").append(lines.length).append(":");
			int length = 0;
			boolean first = true;
			for (String line : lines) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				sb.append(line.length());
				length += line.length();
			}
			sb.append(":").append(length).append(")");
			tagSize.setText(sb.toString());
		}
	}

	public DemoViewHolder setShowMeta(boolean showMeta) {
		this.showMeta = showMeta;
		if (showMeta) {
			tagSize.setVisibility(View.VISIBLE);
		} else {
			tagSize.setVisibility(View.GONE);
		}
		return this;
	}
}
