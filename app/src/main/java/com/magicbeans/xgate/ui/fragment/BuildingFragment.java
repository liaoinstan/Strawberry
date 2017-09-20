package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseFragment;


/**
 * Created by liaoinstan
 */
public class BuildingFragment extends BaseFragment {

    private int position;
    private View rootView;

    private TextView text_building;

    public static Fragment newInstance(int position) {
        BuildingFragment fragment = new BuildingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_building, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initData();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
        text_building = (TextView) rootView.findViewById(R.id.text_building);
    }

    private void initData() {
    }

    private void initCtrl() {
        text_building.setText("building " + position);
    }
}
