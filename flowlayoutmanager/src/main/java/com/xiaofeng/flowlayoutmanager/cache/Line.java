package com.xiaofeng.flowlayoutmanager.cache;

/**
 * Created by xhan on 4/28/16.
 */
public class Line {
	public int itemCount;
	public int totalWidth;
	public int maxHeight;

	public static final Line EMPTY_LINE = new Line();
	public Line() {
		itemCount = 0;
		totalWidth = 0;
		maxHeight = 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Line line = (Line) o;

		if (itemCount != line.itemCount) return false;
		if (totalWidth != line.totalWidth) return false;
		return maxHeight == line.maxHeight;

	}

	@Override
	public int hashCode() {
		int result = itemCount;
		result = 31 * result + totalWidth;
		result = 31 * result + maxHeight;
		return result;
	}

	public Line clone() {
		Line clone = new Line();
		clone.itemCount = itemCount;
		clone.totalWidth = totalWidth;
		clone.maxHeight = maxHeight;
		return clone;
	}
}
