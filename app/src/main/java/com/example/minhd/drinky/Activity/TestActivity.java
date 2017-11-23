package com.example.minhd.drinky.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minhd.drinky.Fragment.MapFragment;
import com.example.minhd.drinky.Item;
import com.example.minhd.drinky.R;
import com.example.minhd.drinky.TabMessage;
import com.example.minhd.drinky.adapterFavorite;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
public class TestActivity extends AppCompatActivity implements adapterFavorite.ICom {


    private Spinner spKhuVuc, spSapXep ;
    private adapterFavorite adapterr;
    private RecyclerView rclLinea;
    private List<Item> list;
    private View view;
    private ArrayList<Item> listRcl;
    private BottomBar bottomBar;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_test);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_star);
        bottomBar.setActiveTabColor(Color.parseColor("#f8f16b"));
        spKhuVuc = findViewById(R.id.spn_khuvuc);
        spSapXep = findViewById(R.id.spn_sapxep);

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

                }
                if (TabMessage.getPos(tabId, false) == 4) {

                    Intent intent1 = new Intent(getBaseContext(), ProfileActivity.class);
                    startActivity(intent1);
                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                Toast.makeText(getBaseContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });

        List<String> listKV = new ArrayList<>();
        listKV.add("Hà Nội");
        listKV.add("Hồ Chí Minh");
        listKV.add("Đà Nẵng");

        List<String> listSapxep = new ArrayList<>();
        listSapxep.add("Tốt Nhất");
        listSapxep.add("Đẹp Nhất");

        ArrayAdapter<String> adapterSX = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listSapxep);
        adapterSX.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spSapXep.setAdapter(adapterSX);
        spSapXep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), spSapXep.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listKV);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spKhuVuc.setAdapter(adapter);
        spKhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), spKhuVuc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterr = new adapterFavorite(this);
        rclLinea = (RecyclerView) findViewById(R.id.rcl_linear);
        rclLinea.setLayoutManager(new LinearLayoutManager(this));
        rclLinea.setAdapter(adapterr);
        rclLinea.smoothScrollToPosition(0);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new Item("Ding Tea", "112 Trần Duy Hưng", R.drawable.dingtea)) ;
        list.add(new Item("Toco Toco", "112 Xuân Thủy", R.drawable.toco)) ;
        list.add(new Item("Feeling Tea", "20 Hồ Tùng Mậu", R.drawable.foody)) ;
        list.add(new Item("Ding Tea", "112 Trần Duy Hưng", R.drawable.dingtea)) ;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Item getItem(int position) {
        return list.get(position);
    }

}
