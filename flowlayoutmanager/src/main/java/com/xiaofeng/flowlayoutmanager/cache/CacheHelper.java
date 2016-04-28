package com.xiaofeng.flowlayoutmanager.cache;

import android.graphics.Point;
import android.util.SparseArray;

import com.xiaofeng.flowlayoutmanager.FlowLayoutOptions;

/**
 * Created by xhan on 4/27/16.
 */
public class CacheHelper {
	public static final int NOT_FOUND = -1;
	final int itemPerLine;
	final int contentAreaWidth;
	SparseArray<Point> sizeMap;
	SparseArray<Line> lineMap;

	public CacheHelper(FlowLayoutOptions layoutOptions, int contentAreaWidth) {
		this.itemPerLine = layoutOptions.itemsPerLine;
		this.contentAreaWidth = contentAreaWidth;
		sizeMap = new SparseArray<>();
		lineMap = new SparseArray<>();
	}

	public void add(int startIndex, Point... sizes) {
		makeSpace(startIndex, sizes.length);
		int index = startIndex;
		for (Point size : sizes) {
			sizeMap.put(index ++, size);
		}
		rebuildLineMap();
	}

	public void add(int startIndex, int count) {
		makeSpace(startIndex, count);
		rebuildLineMap();
	}

	public void invalidSizes(int index, int count) {
		int actualCount = actualCount(index, count);
		for (int i = 0; i < actualCount; i ++) {
			sizeMap.remove(index + i);
		}
		rebuildLineMap();
	}

	public void remove(int index, int count) {
		int actualCount = actualCount(index, count);
		for (int i = 0; i < actualCount; i ++) {
			sizeMap.remove(index + i);
		}

		// move everything behind to fill the hole.
		for (int i = index + actualCount; i < sizeMap.size() + actualCount; i ++) {
			Point tmp = sizeMap.get(i);
			sizeMap.remove(i);
			sizeMap.put(i - actualCount, tmp);
		}

		rebuildLineMap();
	}

	/**
	 * Move items from one place to another. no check on parameter as invoker will make sure it is correct
	 */
	public void move(int from, int to, int count) {
		Point[] itemsToMove = new Point[count];
		for (int i = from; i < from + count; i ++) {
			itemsToMove[i - from] = sizeMap.get(i);
		}
		boolean movingForward = from - to > 0;
		int itemsToShift = Math.abs(from - to);

		if (!movingForward) {
			itemsToShift -= count;
		}
		int shiftIndex = movingForward ? from - 1 : from + count;
		int shiftIndexStep = movingForward ? -1 : 1;

		int shifted = 0;
		while (shifted < itemsToShift) {
			sizeMap.put(shiftIndex - (shiftIndexStep) * count, sizeMap.get(shiftIndex));
			shiftIndex += shiftIndexStep;
			shifted ++;
		}

		int setIndex = to;
		if (!movingForward) {
			setIndex = from + itemsToShift;
		}
		for (Point item : itemsToMove) {
			sizeMap.put(setIndex++, item);
		}
		rebuildLineMap();
	}

	public int[] getLineMap() {
		int[] lineCounts = new int[this.lineMap.size()];
		for (int i = 0; i < this.lineMap.size(); i ++) {
			lineCounts[i] = this.lineMap.get(i).itemCount;
		}
		return lineCounts;
	}

	public int itemLineIndex(int itemIndex) {
		int itemCount = 0;
		for (int i = 0; i < lineMap.size(); i ++) {
			itemCount += lineMap.get(i).itemCount;
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
		return !lineMap.get(lineIndex + 1, Line.EMPTY_LINE).equals(Line.EMPTY_LINE);
	}

	public void clear() {
		sizeMap.clear();
		lineMap.clear();
	}
	//===================== Helper methods ========================

	/**
	 * Move item after startIndex to make {count} space(s)
	 */
	private void makeSpace(int startIndex, int count) {
		for (int i = sizeMap.size() - 1; i >= startIndex; i --) {
			sizeMap.put(i + count, sizeMap.get(i));
		}
		for (int i = startIndex; i < startIndex + count; i ++) {
			sizeMap.remove(i);
		}
	}

	/**
	 * Rebuild line map. and should stop if there is a hole (like item changed or item inserted but not measured)
	 */
	private void rebuildLineMap() {
		lineMap.clear();
		int index = 0;
		Point cachedSize = sizeMap.get(index, null);
		int lineWidth = 0;
		int lineIndex = 0;
		int lineItemCount = 0;
		Line currentLine = new Line();

		while (cachedSize != null) {
			lineWidth += cachedSize.x;
			lineItemCount ++;
			if (lineWidth <= contentAreaWidth) {
				if (itemPerLine > 0) { // have item per line limit
					if (lineItemCount > itemPerLine) { // exceed item per line limit
						lineMap.put(lineIndex, currentLine);

						// put this item to next line
						currentLine = new Line();
						addToLine(currentLine, cachedSize);
						lineIndex ++;
						lineWidth = cachedSize.x;
						lineItemCount = 1;
					} else {
						addToLine(currentLine, cachedSize);
					}
				} else {
					addToLine(currentLine, cachedSize);
				}
			} else { // too wide to add this item, put line item count to index and put this one to new line
				lineMap.put(lineIndex, currentLine);
				currentLine = new Line();
				addToLine(currentLine, cachedSize);
				lineIndex ++;
				lineWidth = cachedSize.x;
				lineItemCount = 1;

			}
			index ++;
			cachedSize = sizeMap.get(index, null);
		}

		if (currentLine.itemCount > 0) {
			lineMap.append(lineIndex, currentLine);
		}
	}

	/**
	 * Add view info to line
	 */
	private void addToLine(Line line, Point item) {
		line.itemCount ++;
		line.totalWidth += item.x;
		line.maxHeight = item.y > line.maxHeight ? item.y : line.maxHeight;
	}

	/**
	 * return actual count from index to expected count or end of sizeMap
	 */
	private int actualCount(int index, int count) {
		return index + count > sizeMap.size() ? sizeMap.size() - index : count;
	}
}
