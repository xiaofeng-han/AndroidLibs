package com.xiaofeng.androidlibs;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.Objects;

/**
 * Demo of FlowLayoutManager. It uses only framework widgets and RecyclerView, so that the sample
 * runs on the same minSdk as the library.
 */
public class MainActivity extends Activity {

    private static final int DEMO_MAX_LINES = 3;

    RecyclerView recyclerView;
    FlowLayoutManager flowLayoutManager;
    RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(5, 5, 5, 5);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(new DemoAdapter(DemoUtil.listWords()));
        recyclerView.addItemDecoration(itemDecoration);
        setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.LEFT));

        findViewById(R.id.navigation_left).setOnClickListener(v ->
                setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.LEFT)));
        findViewById(R.id.navigation_center).setOnClickListener(v ->
                setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.CENTER)));
        findViewById(R.id.navigation_right).setOnClickListener(v ->
                setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.RIGHT)));
        findViewById(R.id.navigation_max_lines).setOnClickListener(v ->
                setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.LEFT).maxLines(DEMO_MAX_LINES)));
    }

    private void setLayoutManager(FlowLayoutManager layoutManager) {
        flowLayoutManager = layoutManager;
        flowLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(flowLayoutManager);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }
}
