package com.jb.caesarfeng.swiperefresh;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mLv;

    private List<String> mData = new ArrayList<>();

    private BaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLv = (ListView) findViewById(R.id.lv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);

        initData();
        initLv();
        initSwip();
    }

    private void initSwip() {
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#00ff00"));
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        int distance = getResources().getDisplayMetrics().heightPixels/10;
        mSwipeRefreshLayout.setDistanceToTriggerSync(distance);
        //加载的视图在哪里显示
        mSwipeRefreshLayout.setProgressViewEndTarget(true,100);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initLv() {
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);
        mLv.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            mData.add("你猜我猜你猜不猜");
        }
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    mLv.post(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 10; i++) {
                                mData.add(0,"猜你个大头鬼...");
                            }
                            mAdapter.notifyDataSetChanged();
                            if (mSwipeRefreshLayout.isRefreshing()){
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
