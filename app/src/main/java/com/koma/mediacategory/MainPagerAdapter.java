package com.koma.mediacategory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koma on 2017/1/14.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = MainPagerAdapter.class.getSimpleName();
    private static final int TAB_COUNT = 3;

    private String[] mTitles;

    private List<Fragment> mFragments = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
