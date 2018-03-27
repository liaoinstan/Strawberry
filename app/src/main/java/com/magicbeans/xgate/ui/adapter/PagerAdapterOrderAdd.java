package com.magicbeans.xgate.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.magicbeans.xgate.ui.fragment.OrderAddCommitFragment;
import com.magicbeans.xgate.ui.fragment.OrderAddInputFragment;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterOrderAdd extends FragmentPagerAdapter {

    private String[] titles;

    public PagerAdapterOrderAdd(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
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
                return OrderAddInputFragment.newInstance(position);
            case 1:
                return OrderAddCommitFragment.newInstance(position);
            default:
                return null;
        }
    }
}
