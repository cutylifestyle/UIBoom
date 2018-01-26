package com.sixin.uiboom;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lehand.indicator.TabPageIndicator;
import com.sixin.uiboom.permissionsutil.PermissionsUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private TabPageIndicator indicator;
    private ViewPager viewPager;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PermissionsUtil.requestPermissions(this,
                100,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        indicator = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.viewPager);
        for(int i = 0 ; i < 100 ; i++){
            data.add("第" + i + "个");
        }
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        indicator.setViewPager(viewPager);
        int a ='5';
        Log.d(TAG,"...."+a);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsUtil.onRequestPermissionsResult(this,
                100,
                permissions,
                grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    class MyAdapter extends FragmentStatePagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BlankFragment.newInstance();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position);
        }
    }
}
