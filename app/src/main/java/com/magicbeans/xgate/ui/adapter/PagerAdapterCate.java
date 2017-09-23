package com.magicbeans.xgate.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.magicbeans.xgate.ui.fragment.BuildingFragment;
import com.magicbeans.xgate.ui.fragment.CateInFragment;


/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterCate extends FragmentPagerAdapter {

    private String[] titles;

    public PagerAdapterCate(FragmentManager fm, String[] titles) {
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
                return CateInFragment.newInstance(position);
            case 1:
                return BuildingFragment.newInstance(position);
            default:
                return null;
        }
    }
}
