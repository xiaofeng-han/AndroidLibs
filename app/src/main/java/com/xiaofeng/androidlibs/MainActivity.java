package com.xiaofeng.androidlibs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import us.feras.mdv.MarkdownView;

public class MainActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	FlowLayoutManager flowLayoutManager;
	MarkdownView markdownView;
	private static final int REQ_CODE_SETTINGS = 101;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_bar_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		init();
	}

	private void init() {
		recyclerView = (RecyclerView) findViewById(R.id.list);
		flowLayoutManager = new FlowLayoutManager().singleItemPerLine();
		flowLayoutManager.setAutoMeasureEnabled(true);
		recyclerView.setLayoutManager(flowLayoutManager);
		recyclerView.setAdapter(new DemoAdapter(1, DemoUtil.generate(2000, 3, 13, 1, false)));
		recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.set(5, 5, 5, 5);
			}
		});

		markdownView = (MarkdownView)findViewById(R.id.instruction_mdown);
		markdownView.loadMarkdownFile("file:///android_asset/instruction.md");
		loadSettingsFromSharedPref();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			startActivityForResult(new Intent(this, SettingsActivity.class), REQ_CODE_SETTINGS);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadSettingsFromSharedPref() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String itemsPerLineString = sharedPreferences.getString(getResources().getString(R.string.pref_key_max_items_per_line), getString(R.string.pref_max_items_per_line_default));
		int itemsPerLine = Integer.valueOf(itemsPerLineString);

		String alignmentString = sharedPreferences.getString(getResources().getString(R.string.pref_key_alignment), getString(R.string.pref_alignment_default));
		int alignmentInt = Integer.valueOf(alignmentString);
		Alignment[] alignments = Alignment.values();
		Alignment selectedAlignment = Alignment.LEFT;
		for (Alignment alignment : alignments) {
			if (alignment.ordinal() == alignmentInt) {
				selectedAlignment = alignment;
				break;
			}
		}
//		boolean showMeta = sharedPreferences.getBoolean(getString(R.string.pref_key_show_meta), false);

		flowLayoutManager.maxItemsPerLine(itemsPerLine);
		flowLayoutManager.setAlignment(selectedAlignment);
		DemoAdapter demoAdapter = (DemoAdapter)recyclerView.getAdapter();
//		demoAdapter.setShowMeta(showMeta);
		String maxLinesPerItemString = sharedPreferences.getString(getString(R.string.pref_key_max_lines_per_item), getString(R.string.pref_max_lines_per_item_default));
		int maxLinesPerItem = Integer.valueOf(maxLinesPerItemString);
		demoAdapter.newItems(maxLinesPerItem, DemoUtil.generate(demoAdapter.getItemCount(), 3, 13, maxLinesPerItem, false));
		recyclerView.getAdapter().notifyItemRangeChanged(0, recyclerView.getAdapter().getItemCount());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_SETTINGS) {
			new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
				@Override
				public void run() {
					loadSettingsFromSharedPref();
				}
			}, 1000);
		}
	}
}
