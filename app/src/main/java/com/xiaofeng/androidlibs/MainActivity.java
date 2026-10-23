package com.xiaofeng.androidlibs;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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

    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener
            = new NavigationBarView.OnItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Resource ids are not final in AGP 8+, so they cannot be used in a switch.
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_left) {
                setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.LEFT));
                return true;
            } else if (itemId == R.id.navigation_center) {
                setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.CENTER));
                return true;
            } else if (itemId == R.id.navigation_right) {
                setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.RIGHT));
                return true;
            } else if (itemId == R.id.navigation_max_lines) {
                setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.LEFT).maxLines(DEMO_MAX_LINES));
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(onItemSelectedListener);
        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(new DemoAdapter(DemoUtil.listWords()));
        recyclerView.addItemDecoration(itemDecoration);
        setLayoutManager(new FlowLayoutManager().setAlignment(Alignment.LEFT));
    }

    private void setLayoutManager(FlowLayoutManager layoutManager) {
        flowLayoutManager = layoutManager;
        flowLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(flowLayoutManager);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }
}
