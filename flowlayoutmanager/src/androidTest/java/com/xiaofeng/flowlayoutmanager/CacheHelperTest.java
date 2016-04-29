package com.xiaofeng.flowlayoutmanager;

import android.graphics.Point;
import android.test.InstrumentationTestCase;

import com.xiaofeng.flowlayoutmanager.cache.CacheHelper;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by xhan on 4/27/16.
 */
public class CacheHelperTest extends InstrumentationTestCase {
	@Test
	public void testLineCountsNoLimit() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = FlowLayoutOptions.ITEM_PER_LINE_NO_LIMIT;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);
		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1), new Point(2, 1)
				, new Point(9, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);
		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("lineCounts should have 4 items", lineCounts.length, is(4));
		assertThat("line 0 have 2 items", lineCounts[0], is(2));
		assertThat("line 1 have 3 items", lineCounts[1], is(3));
		assertThat("line 2 have 1 items", lineCounts[2], is(1));
		assertThat("line 3 have 2 items", lineCounts[3], is(2));
	}

	@Test
	public void testLineCountsWithLimit() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = 2;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);
		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1)
				, new Point(2, 1)
				, new Point(9, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);
		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("lineCounts should have 5 items", lineCounts.length, is(5));
		assertThat("line 0 have 2 items", lineCounts[0], is(2));
		assertThat("line 1 have 2 items", lineCounts[1], is(2));
		assertThat("line 2 have 1 items", lineCounts[2], is(1));
		assertThat("line 3 have 1 items", lineCounts[3], is(1));
		assertThat("line 4 have 2 items", lineCounts[4], is(2));
	}

	public void testItemLineIndex() {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = 2;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);
		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1)
				, new Point(2, 1)
				, new Point(9, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		assertThat("1st item have line index 0", cacheHelper.itemLineIndex(0), is(0));
		assertThat("3rd item have line index 1", cacheHelper.itemLineIndex(2), is(1));
		assertThat("7th item have line index 4", cacheHelper.itemLineIndex(6), is(4));
		assertThat("8th item have line index 4", cacheHelper.itemLineIndex(7), is(4));
		assertThat("9th item have line index -1", cacheHelper.itemLineIndex(8), is(CacheHelper.NOT_FOUND));
	}

	public void testHavePreviousLineCached() {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = 2;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);
		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1)
				, new Point(2, 1)
				, new Point(9, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		assertThat("1st item do not have previous line cached", cacheHelper.hasPreviousLineCached(0), is(false));
		assertThat("3rd item have previous line cached", cacheHelper.hasPreviousLineCached(2), is(true));
		assertThat("8th item have previous line cached", cacheHelper.hasPreviousLineCached(7), is(true));
		assertThat("9th item do not have previous line cached", cacheHelper.hasPreviousLineCached(9), is(false));
	}

	public void testHaveNextLineCached() {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = 2;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);
		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1)
				, new Point(2, 1)
				, new Point(9, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);
		assertThat("1st item have next line cached", cacheHelper.hasNextLineCached(0), is(true));
		assertThat("3rd item have next line cached", cacheHelper.hasNextLineCached(2), is(true));
		assertThat("8th item does not have next line cached", cacheHelper.hasNextLineCached(7), is(false));
		assertThat("9th item do not have next line cached", cacheHelper.hasNextLineCached(9), is(false));
	}

	public void testRemoveMiddle() {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = 2;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);
		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1)
				, new Point(2, 1)
				, new Point(9, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		cacheHelper.remove(3, 1); // second 4 in second line

		// after remove it should be
		// 5, 2
		// 4, 2
		// 9
		// 2, 3

		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("line counts should have 4 lines", lineCounts.length, is(4));
		assertThat("line 1 have 2 items", lineCounts[0], is(2));
		assertThat("line 2 have 2 items", lineCounts[1], is(2));
		assertThat("line 3 have 1 items", lineCounts[2], is(1));
		assertThat("line 4 have 1 items", lineCounts[3], is(2));

		cacheHelper.remove(4, 1); // 9 is removed
		// after removal it should be
		// 5, 2
		// 4, 2
		// 2, 3
		lineCounts = cacheHelper.getLineMap();
		assertThat("line counts should have 3 lines", lineCounts.length, is(3));
		assertThat("line 1 have 2 items", lineCounts[0], is(2));
		assertThat("line 2 have 3 items", lineCounts[1], is(2));
		assertThat("line 3 have 1 items", lineCounts[2], is(2));
	}

	public void testAdd() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = FlowLayoutOptions.ITEM_PER_LINE_NO_LIMIT;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);

		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1), new Point(2, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		cacheHelper.add(3, new Point(9, 2), new Point(1,1), new Point(2, 2));
		// after addition it should be:
		// 5, 2
		// 4
		// 9, 1
		// 2, 4, 2, 2
		// 3

		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("should have 5 lines", lineCounts.length, is(5));

		assertThat("line 1 have 2 items", lineCounts[0], is(2));
		assertThat("line 2 have 1 items", lineCounts[1], is(1));
		assertThat("line 3 have 2 items", lineCounts[2], is(2));
		assertThat("line 4 have 4 items", lineCounts[3], is(4));
		assertThat("line 5 have 1 items", lineCounts[4], is(1));
	}

	public void testRemoveMultiple() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = FlowLayoutOptions.ITEM_PER_LINE_NO_LIMIT;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);

		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1), new Point(2, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		cacheHelper.remove(3, 2);
		// after Remove it should be:
		// 5, 2
		// 4, 2, 3

		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("should have 2 lines", lineCounts.length, is(2));

		assertThat("line 1 have 2 items", lineCounts[0], is(2));
		assertThat("line 2 have 3 items", lineCounts[1], is(3));
	}

	public void testInvalidateSize() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = FlowLayoutOptions.ITEM_PER_LINE_NO_LIMIT;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);

		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1), new Point(2, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		cacheHelper.invalidSizes(4, 2);

		// after invalidate it should be:
		// 5, 2
		// 4, 4

		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("should have 2 lines", lineCounts.length, is(2));

		assertThat("line 1 have 2 items", lineCounts[0], is(2));
		assertThat("line 2 have 2 items", lineCounts[1], is(2));
	}

	public void testOnItemInserted() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = FlowLayoutOptions.ITEM_PER_LINE_NO_LIMIT;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);

		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1), new Point(2, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		cacheHelper.add(3, 2);

		// after onItemInserted
		// 5, 2
		// 4
		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("line counts have 2 items", lineCounts.length, is(2));
		assertThat("line 1 have 2 items", lineCounts[0], is(2));
		assertThat("line 2 have 1 items", lineCounts[1], is(1));
	}

	public void testRemoveBeyondEnd() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = FlowLayoutOptions.ITEM_PER_LINE_NO_LIMIT;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);

		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1), new Point(2, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		cacheHelper.remove(5, 10);
		// should be
		// 5, 2,
		// 4, 4, 2

		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("line counts have 2 items", lineCounts.length, is(2));
		assertThat("line 1 have 2 items", lineCounts[0], is(2));
		assertThat("line 2 have 3 items", lineCounts[1], is(3));
	}

	public void testMoveForward() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = FlowLayoutOptions.ITEM_PER_LINE_NO_LIMIT;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);

		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1), new Point(2, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		cacheHelper.move(5, 1, 2);

		// after move
		// 5, 2, 3
		// 2, 4, 4
		// 2

		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("line counts have 3 items", lineCounts.length, is(3));
		assertThat("line 1 have 3 items", lineCounts[0], is(3));
		assertThat("line 2 have 3 items", lineCounts[1], is(3));
		assertThat("line 3 have 1 items", lineCounts[2], is(1));
	}

	public void testMoveBackward() throws Exception {
		FlowLayoutOptions layoutOptions = new FlowLayoutOptions();
		layoutOptions.itemsPerLine = FlowLayoutOptions.ITEM_PER_LINE_NO_LIMIT;
		int contentAreaWidth = 10;
		CacheHelper cacheHelper = new CacheHelper(layoutOptions, contentAreaWidth);

		Point[] items = new Point[] {
				new Point(5, 1), new Point(2, 1)
				, new Point(4, 1), new Point(4, 1), new Point(2, 1)
				, new Point(2, 1), new Point(3, 2)};
		cacheHelper.add(0, items);

		cacheHelper.move(3, 6, 2);

		// after move
		// 5, 2
		// 4, 2, 4
		// 2, 3

		int[] lineCounts = cacheHelper.getLineMap();
		assertThat("line counts have 3 items", lineCounts.length, is(3));
		assertThat("line 1 have 3 items", lineCounts[0], is(2));
		assertThat("line 2 have 3 items", lineCounts[1], is(3));
		assertThat("line 3 have 1 items", lineCounts[2], is(2));
	}
}
