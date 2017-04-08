package com.jb.caesarfeng.day25_pullsliding;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Handler.Callback{

    private PullToRefreshListView mPullToRefreshListView;
    private List<String> mData = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private Handler mHandler = new Handler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullLv);

        for (int i = 0; i < 100; i++) {
            mData.add(String.format("HelloWorld%s","Android"+i));
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);

        mPullToRefreshListView.setAdapter(adapter);

        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //联网请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);

                            mHandler.sendEmptyMessage(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {

        switch(msg.what){
            case 200:
                mData.clear();
                for (int i = 0; i < 100; i++) {
                    mData.add(String.format(Locale.CHINA,"第%d只%s",i,"蛤蟆"));
                }
                adapter.notifyDataSetChanged();

                if (mPullToRefreshListView.isRefreshing()) {
                    mPullToRefreshListView.onRefreshComplete();
                }
                break;

             default :

                break;

        }


        return false;
    }
}
