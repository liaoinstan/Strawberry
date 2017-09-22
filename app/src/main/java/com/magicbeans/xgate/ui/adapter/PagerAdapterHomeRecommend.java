package com.magicbeans.xgate.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.magicbeans.xgate.ui.fragment.BuildingFragment;
import com.magicbeans.xgate.ui.fragment.HomeFragment;


/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterHomeRecommend extends FragmentPagerAdapter {

    private String[] titles;

    public PagerAdapterHomeRecommend(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BuildingFragment.newInstance(position);
            case 1:
                return BuildingFragment.newInstance(position);
            case 2:
                return BuildingFragment.newInstance(position);
            case 3:
                return BuildingFragment.newInstance(position);
            default:
                return null;
        }
    }
}
