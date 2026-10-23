package com.xiaofeng.flowlayoutmanager;

/**
 * Created by xhan on 4/11/16.
 */
public class FlowLayoutOptions {
	public static final int ITEM_PER_LINE_NO_LIMIT = 0;
	public static final int MAX_LINES_NO_LIMIT = 0;
    public Alignment alignment = Alignment.CENTER;
    public int itemsPerLine = ITEM_PER_LINE_NO_LIMIT;
    /**
     * Max number of lines to show, items after the last line are not laid out.
     * {@link #MAX_LINES_NO_LIMIT} (or any value &lt;= 0) shows all lines.
     */
    public int maxLines = MAX_LINES_NO_LIMIT;

	public static FlowLayoutOptions clone(FlowLayoutOptions layoutOptions) {
		FlowLayoutOptions result = new FlowLayoutOptions();
		result.alignment = layoutOptions.alignment;
		result.itemsPerLine = layoutOptions.itemsPerLine;
		result.maxLines = layoutOptions.maxLines;
		return result;
	}
}
