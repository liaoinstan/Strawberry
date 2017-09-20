package com.magicbeans.xgate.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.magicbeans.xgate.ui.fragment.BuildingFragment;
import com.magicbeans.xgate.ui.fragment.HomeFragment;


/**
 * Created by Administrator on 2017/7/7.
 */

public class PagerAdapterHome extends FragmentPagerAdapter {

    public PagerAdapterHome(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance(position);
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
