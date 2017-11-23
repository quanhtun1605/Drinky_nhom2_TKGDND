package com.example.minhd.drinky.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.minhd.drinky.Fragment.MapFragment;
import com.example.minhd.drinky.Pager;
import com.example.minhd.drinky.R;

public class Store extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private int[] images = {R.drawable.dingtea_show, R.drawable.dingteaa, R.drawable.foody};

    private View cell;
    private LinearLayout mainLayout;
    private ViewPager viewPager;
    private ImageView imgBack, imgSearch ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_store);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }catch (NullPointerException e) {}
        mToolbar.setOverflowIcon(getDrawable(R.drawable.ic_action_name));
        mToolbar.getBackground().setAlpha(0);
        mToolbar.showOverflowMenu();
        mToolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgBack = findViewById(R.id.img_back);
        imgSearch = findViewById(R.id.img_searchStore);

        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        viewPager = findViewById(R.id._viewPagerStore);

        mainLayout = findViewById(R.id.linearLayoutStore);

        for (int i = 0; i < images.length; i++) {

            cell = getLayoutInflater().inflate(R.layout.cell, null);

            final ImageView imageView = cell.findViewById(R.id._image);

            imageView.setOnClickListener(v -> {

                viewPager.setVisibility(View.VISIBLE);
                viewPager.setAdapter
                        (new Pager(Store.this, images));
                viewPager.setCurrentItem(v.getId());
            });

            imageView.setId(i);


            imageView.setImageResource(images[i]);

            mainLayout.addView(cell);
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back:
                Intent intent = new Intent(getBaseContext(), MapFragment.class);
                startActivity(intent);
                break;

            case R.id.img_searchStore:

                break;

            default:

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), MapFragment.class);
        startActivity(intent);
    }
}
