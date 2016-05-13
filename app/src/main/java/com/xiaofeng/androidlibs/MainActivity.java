package com.xiaofeng.androidlibs;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import us.feras.mdv.MarkdownView;

public class MainActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	Spinner itemPerLineSpinner, alignmentSpinner;
	ArrayAdapter<CharSequence> itemsPerLineAdapter, alignmentAdapter;
	FlowLayoutManager flowLayoutManager;
	MarkdownView markdownView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_bar_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		init();
	}

	private void init() {
		itemPerLineSpinner = (Spinner)findViewById(R.id.spinner_items_per_line);
		itemsPerLineAdapter = ArrayAdapter.createFromResource(this, R.array.item_per_line_options, android.R.layout.simple_spinner_dropdown_item);
		itemPerLineSpinner.setAdapter(itemsPerLineAdapter);
		itemPerLineSpinner.setSelection(itemsPerLineAdapter.getPosition(getText(R.string.line_option_no)));
		itemPerLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				updateListBySpinners();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		alignmentSpinner = (Spinner)findViewById(R.id.spinner_alignment);
		alignmentAdapter = ArrayAdapter.createFromResource(this, R.array.alignment_options, android.R.layout.simple_spinner_dropdown_item);
		alignmentSpinner.setAdapter(alignmentAdapter);
		alignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				updateListBySpinners();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		recyclerView = (RecyclerView) findViewById(R.id.list);
		flowLayoutManager = new FlowLayoutManager().singleItemPerLine();
		flowLayoutManager.setAutoMeasureEnabled(true);
		recyclerView.setLayoutManager(flowLayoutManager);
		recyclerView.setAdapter(new DemoAdapter(DemoUtil.generate(2000, 3, 13, 1, false)));
		recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.set(5, 5, 5, 5);
			}
		});

		markdownView = (MarkdownView)findViewById(R.id.instruction_mdown);
		markdownView.loadMarkdownFile("file:///android_asset/instruction.md");
	}

	private void updateListBySpinners() {
		CharSequence itemsPerLine = itemsPerLineAdapter.getItem(itemPerLineSpinner.getSelectedItemPosition());
		CharSequence alignment = alignmentAdapter.getItem(alignmentSpinner.getSelectedItemPosition());
		if (itemsPerLine.equals(getText(R.string.line_option_one))) {
			flowLayoutManager.maxItemsPerLine(1);
		} else if (itemsPerLine.equals(getText(R.string.line_option_two))) {
			flowLayoutManager.maxItemsPerLine(2);
		} else if (itemsPerLine.equals(getText(R.string.line_option_three))) {
			flowLayoutManager.maxItemsPerLine(3);
		} else if (itemsPerLine.equals(getText(R.string.line_option_no))) {
			flowLayoutManager.removeItemPerLineLimit();
		}

		if (alignment.equals(getText(R.string.alignment_left))) {
			flowLayoutManager.setAlignment(Alignment.LEFT);
		} else if (alignment.equals(getText(R.string.alignment_right))) {
			flowLayoutManager.setAlignment(Alignment.RIGHT);
		}
		recyclerView.getAdapter().notifyItemRangeChanged(0, recyclerView.getAdapter().getItemCount());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
