package com.magicbeans.xgate.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.magicbeans.xgate.ui.fragment.BuildingFragment;
import com.magicbeans.xgate.ui.fragment.CateFragment;
import com.magicbeans.xgate.ui.fragment.HomeFragment;
import com.magicbeans.xgate.ui.fragment.MeFragment;
import com.magicbeans.xgate.ui.fragment.ShopBagFragment;


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
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance(position);
            case 1:
                return CateFragment.newInstance(position);
            case 2:
                return ShopBagFragment.newInstance(position);
            case 3:
                return MeFragment.newInstance(position);
            default:
                return null;
        }
    }
}
