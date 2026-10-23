package com.xiaofeng.flowlayoutmanager;

import android.app.Activity;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Lays out fixed size items: 3 items of {@link #ITEM_WIDTH} fit in a line of {@link #LIST_WIDTH},
 * and {@link #LIST_HEIGHT} shows 8 lines of {@link #ITEM_HEIGHT}.
 */
@RunWith(AndroidJUnit4.class)
public class FlowLayoutManagerTest {
	private static final int ITEM_WIDTH = 100;
	private static final int ITEM_HEIGHT = 50;
	private static final int LIST_WIDTH = 300;
	private static final int LIST_HEIGHT = 400;
	private static final int ITEM_COUNT = 30;

	private Activity activity;
	private RecyclerView recyclerView;

	@Before
	public void setUp() {
		activity = Robolectric.buildActivity(Activity.class).setup().get();
		recyclerView = new RecyclerView(activity);
		recyclerView.setAdapter(new FixedSizeAdapter(ITEM_COUNT));
	}

	@Test
	public void layoutOnlyVisibleLines() {
		attach(new FlowLayoutManager().setAlignment(Alignment.LEFT), LIST_HEIGHT);

		assertThat("8 visible lines of 3 items", recyclerView.getChildCount(), is(24));
		assertThat(recyclerView.canScrollVertically(1), is(true));
	}

	@Test
	public void itemsPerLineLimit() {
		attach(new FlowLayoutManager().setAlignment(Alignment.LEFT).maxItemsPerLine(2), LIST_HEIGHT);

		assertThat("8 visible lines of 2 items", recyclerView.getChildCount(), is(16));
		assertThat("3rd item starts the 2nd line", recyclerView.getChildAt(2).getTop(), is(ITEM_HEIGHT));
	}

	@Test
	public void maxLinesLimitsLaidOutItems() {
		attach(new FlowLayoutManager().setAlignment(Alignment.LEFT).maxLines(2), LIST_HEIGHT);

		assertThat("2 lines of 3 items", recyclerView.getChildCount(), is(6));
		assertThat(lastChildAdapterPosition(), is(5));
		assertThat(recyclerView.canScrollVertically(1), is(false));
	}

	@Test
	public void maxLinesDoesNotScrollPastLimit() {
		attach(new FlowLayoutManager().setAlignment(Alignment.LEFT).maxLines(10), 3 * ITEM_HEIGHT);

		recyclerView.scrollBy(0, 100 * ITEM_HEIGHT);
		idle();

		assertThat("scrolled to the 10th line", lastChildAdapterPosition(), is(29));

		attach(new FlowLayoutManager().setAlignment(Alignment.LEFT).maxLines(5), 3 * ITEM_HEIGHT);
		recyclerView.scrollBy(0, 100 * ITEM_HEIGHT);
		idle();

		assertThat("last item of the 5th line", lastChildAdapterPosition(), is(14));
		assertThat(recyclerView.canScrollVertically(1), is(false));
	}

	@Test
	public void removeMaxLinesLimit() {
		FlowLayoutManager layoutManager = new FlowLayoutManager().setAlignment(Alignment.LEFT).maxLines(1);
		attach(layoutManager, LIST_HEIGHT);
		assertThat(recyclerView.getChildCount(), is(3));

		layoutManager.removeMaxLinesLimit();
		idle();
		assertThat(recyclerView.getChildCount(), is(24));
	}

	@Test
	public void rightAlignment() {
		attach(new FlowLayoutManager().setAlignment(Alignment.RIGHT), LIST_HEIGHT);

		assertThat("first item at right edge", recyclerView.getChildAt(0).getRight(), is(LIST_WIDTH));
	}

	private void attach(FlowLayoutManager layoutManager, int height) {
		if (recyclerView.getParent() != null) {
			((ViewGroup) recyclerView.getParent()).removeView(recyclerView);
		}
		recyclerView.setLayoutManager(layoutManager);
		FrameLayout root = new FrameLayout(activity);
		root.addView(recyclerView, new FrameLayout.LayoutParams(LIST_WIDTH, height));
		activity.setContentView(root);
		idle();
	}

	private void idle() {
		shadowOf(Looper.getMainLooper()).idle();
		// Robolectric does not run layout passes on its own for views added after setup.
		View root = (View) recyclerView.getParent();
		root.measure(View.MeasureSpec.makeMeasureSpec(LIST_WIDTH, View.MeasureSpec.EXACTLY),
				View.MeasureSpec.makeMeasureSpec(10 * LIST_HEIGHT, View.MeasureSpec.AT_MOST));
		root.layout(0, 0, root.getMeasuredWidth(), root.getMeasuredHeight());
		shadowOf(Looper.getMainLooper()).idle();
	}

	private int lastChildAdapterPosition() {
		return recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));
	}

	private static class FixedSizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
		private final int itemCount;

		FixedSizeAdapter(int itemCount) {
			this.itemCount = itemCount;
		}

		@NonNull
		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = new View(parent.getContext());
			view.setLayoutParams(new RecyclerView.LayoutParams(ITEM_WIDTH, ITEM_HEIGHT));
			return new RecyclerView.ViewHolder(view) {
			};
		}

		@Override
		public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		}

		@Override
		public int getItemCount() {
			return itemCount;
		}
	}
}
