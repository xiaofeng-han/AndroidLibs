package com.xiaofeng.flowlayoutmanager;

import android.graphics.Point;
import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * Created by xhan on 4/27/16.
 */
public class CacheHelper {
	public static final int NOT_FOUND = -1;
	final int itemPerLine;
	final int contentAreaWidth;
	SparseArray<Point> sizeMap;
	SparseIntArray lineCounts;

	public CacheHelper(FlowLayoutOptions layoutOptions, int contentAreaWidth) {
		this.itemPerLine = layoutOptions.itemsPerLine;
		this.contentAreaWidth = contentAreaWidth;
		sizeMap = new SparseArray<>();
		lineCounts = new SparseIntArray();
	}

	public void addToCache(int startIndex, Point... sizes) {
		for (int i = sizeMap.size() - 1; i >= startIndex; i --) {
			sizeMap.put(i + 1, sizeMap.get(i));
		}

		int index = startIndex;
		for (Point size : sizes) {
			sizeMap.put(index ++, size);
		}
		rebuildLineCounts();
	}

	public void remove(int index) {
		sizeMap.remove(index);
		for (int i = index + 1; i <= sizeMap.size(); i ++) {
			sizeMap.put(i - 1, sizeMap.get(i));
		}
		rebuildLineCounts();
	}

	private void rebuildLineCounts() {
		lineCounts.clear();
		int index = 0;
		Point cachedSize = sizeMap.get(index, null);
		int lineWidth = 0;
		int line = 0;
		int lineItemCount = 0;

		while (cachedSize != null) {
			lineWidth += cachedSize.x;
			lineItemCount ++;
			if (lineWidth <= contentAreaWidth) {

				// have item per line limit
				if (itemPerLine > 0) {
					// exceed item per line limit
					if (lineItemCount > itemPerLine) {
						lineCounts.put(line, itemPerLine);
						// put this item to next line
						line ++;
						lineWidth = cachedSize.x;
						lineItemCount = 1;
					}
				}
			} else { // too wide to add this item, put line item count to index and put this one to new line
				lineCounts.put(line, lineItemCount - 1);
				line ++;
				lineWidth = cachedSize.x;
				lineItemCount = 1;
			}
			index ++;
			cachedSize = sizeMap.get(index, null);
		}

		if (lineItemCount != 0) {
			lineCounts.append(line, lineItemCount);
		}
	}

	public int[] getLineCounts() {
		int[] lineCounts = new int[this.lineCounts.size()];
		for (int i = 0; i < this.lineCounts.size(); i ++) {
			lineCounts[i] = this.lineCounts.get(i);
		}
		return lineCounts;
	}

	public int itemLineIndex(int itemIndex) {
		int itemCount = 0;
		for (int i = 0; i < lineCounts.size(); i ++) {
			itemCount += lineCounts.get(i);
			if (itemCount >= itemIndex + 1) {
				return i;
			}
		}
		return NOT_FOUND;
	}

	public boolean havePreviousLineCached(int itemIndex) {
		int lineIndex = itemLineIndex(itemIndex);
		if (lineIndex == NOT_FOUND) {
			return false;
		}

		if (lineIndex > 0) {
			return true;
		}
		return false;
	}

	public boolean haveNextLineCached(int itemIndex) {
		int lineIndex = itemLineIndex(itemIndex);
		if (lineIndex == NOT_FOUND) {
			return false;
		}
		return lineCounts.get(lineIndex + 1, -1) > 0;
	}
}
