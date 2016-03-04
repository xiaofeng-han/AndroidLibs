package com.xiaofeng.androidlibs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	RecyclerView recyclerView;
	Spinner itemPerLineSpinner, alignmentSpinner;
	ArrayAdapter<CharSequence> itemsPerLineAdapter, alignmentAdapter;
	FlowLayoutManager flowLayoutManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		init();
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_camera) {
			// Handle the camera action
		} else if (id == R.id.nav_gallery) {

		} else if (id == R.id.nav_slideshow) {

		} else if (id == R.id.nav_manage) {

		} else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
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
		recyclerView.setLayoutManager(flowLayoutManager);
		recyclerView.setAdapter(new TagAdapter(DemoUtil.generate(100, 3, 13)));
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
			flowLayoutManager.setAlignment(FlowLayoutManager.Alignment.LEFT);
		} else if (alignment.equals(getText(R.string.alignment_right))) {
			flowLayoutManager.setAlignment(FlowLayoutManager.Alignment.RIGHT);
		}
		recyclerView.getAdapter().notifyItemRangeChanged(0, recyclerView.getAdapter().getItemCount());
	}
}
