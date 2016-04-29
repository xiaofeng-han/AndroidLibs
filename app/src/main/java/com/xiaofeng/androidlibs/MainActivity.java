package com.xiaofeng.androidlibs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

public class MainActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	Spinner itemPerLineSpinner, alignmentSpinner;
	ArrayAdapter<CharSequence> itemsPerLineAdapter, alignmentAdapter;
	FlowLayoutManager flowLayoutManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_bar_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
				if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
					layoutParams.height = 150;
				} else {
					layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
				}
				recyclerView.setLayoutParams(layoutParams);
			}
		});
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
		flowLayoutManager = new FlowLayoutManager();
		flowLayoutManager.setAutoMeasureEnabled(true);
		recyclerView.setLayoutManager(flowLayoutManager);
		recyclerView.setAdapter(new TagAdapter(DemoUtil.generate(200, 3, 13)));
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
}
