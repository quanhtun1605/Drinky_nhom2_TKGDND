package com.example.minhd.drinky.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.minhd.drinky.Fragment.MapFragment;
import com.example.minhd.drinky.ItemProfile;
import com.example.minhd.drinky.ProfileAdapter;
import com.example.minhd.drinky.R;
import com.example.minhd.drinky.TabMessage;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements ProfileAdapter.ICom {

    private ProfileAdapter adapterr;
    private RecyclerView rclLinea;
    private List<ItemProfile> list;
    private View view;
    private BottomBar bottomBar;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }catch (NullPointerException e) {}

        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_user);
        bottomBar.setActiveTabColor(Color.parseColor("#5db3e8"));
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
//                messageView.setText(TabMessage.get(tabId, false) +"");
                if (TabMessage.getPos(tabId, false) == 2) {
                    Intent intent = new Intent(getBaseContext(), MapFragment.class);
                    startActivity(intent);
                }
                if (TabMessage.getPos(tabId, false) == 1) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                if (TabMessage.getPos(tabId, false) == 3) {
                    Intent intent = new Intent(getBaseContext(), TestActivity.class);
                    startActivity(intent);
                }
                if (TabMessage.getPos(tabId, false) == 4) {


                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                Toast.makeText(getBaseContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });

        adapterr = new ProfileAdapter(this);
        rclLinea = (RecyclerView) findViewById(R.id.rcl_profile);
        rclLinea.setLayoutManager(new LinearLayoutManager(this));
        rclLinea.setAdapter(adapterr);
        rclLinea.smoothScrollToPosition(0);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new ItemProfile(R.drawable.trasua, "View đẹp đồ uống ngon"));
        list.add(new ItemProfile(R.drawable.trasua_feeling, "Giá rẻ phục vụ nhanh"));
        list.add(new ItemProfile(R.drawable.feeling, "Đang khuyến mại nên rất rẻ"));
        list.add(new ItemProfile(R.drawable.trasua, "Ngon, bổ, rẻ"));
        list.add(new ItemProfile(R.drawable.trasua_feeling, "View đẹp đồ uống ngon"));

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ItemProfile getItem(int position) {
        return list.get(position);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
