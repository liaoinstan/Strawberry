package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.ui.activity.OrderActivity;
import com.magicbeans.xgate.ui.activity.OrderAddActivity;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeShopbag;
import com.magicbeans.xgate.ui.base.BaseFragment;


/**
 * Created by liaoinstan
 */
public class ShopBagFragment extends BaseFragment implements View.OnClickListener,OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private SpringView springView;
    private RecyclerView recycle;
    private RecycleAdapterHomeShopbag adapter;
    private TextView btn_right;
    private TextView text_shopbag_checkall;
    private TextView text_shopbag_priceall;
    private TextView btn_go;

    public static Fragment newInstance(int position) {
        ShopBagFragment fragment = new ShopBagFragment();
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
        rootView = inflater.inflate(R.layout.fragment_shopbag, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        springView = (SpringView) rootView.findViewById(R.id.spring);
        recycle = (RecyclerView) rootView.findViewById(R.id.recycle);
        btn_right = (TextView) rootView.findViewById(R.id.btn_right);
        text_shopbag_checkall = (TextView) rootView.findViewById(R.id.text_shopbag_checkall);
        text_shopbag_priceall = (TextView) rootView.findViewById(R.id.text_shopbag_priceall);
        btn_go = (TextView) rootView.findViewById(R.id.btn_go);
        btn_right.setOnClickListener(this);
        btn_go.setOnClickListener(this);
        text_shopbag_checkall.setOnClickListener(this);
    }

    private void initData() {
        //限时促销列表假数据
        adapter.getResults().clear();
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.notifyDataSetChanged();

    }

    private void initCtrl() {
        adapter = new RecycleAdapterHomeShopbag(getContext());
        adapter.setOnItemClickListener(this);
        recycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycle.addItemDecoration(new ItemDecorationDivider(getContext(), LinearLayoutManager.VERTICAL));
        recycle.setAdapter(adapter);
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        ProductDetailActivity.start(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_shopbag_checkall:
                if (adapter.isSelectAll()) {
                    text_shopbag_checkall.setSelected(false);
                    adapter.selectAll(false);
                }else {
                    text_shopbag_checkall.setSelected(true);
                    adapter.selectAll(true);
                }
                break;
            case R.id.btn_right:
                adapter.setEdit(!adapter.isEdit());
                btn_right.setText(adapter.isEdit() ? "完成" : "编辑");
                btn_go.setText(adapter.isEdit() ? "删除商品" : "结算");
                text_shopbag_priceall.setVisibility(adapter.isEdit() ? View.GONE : View.VISIBLE);
                break;
            case R.id.btn_go:
                if (adapter.isEdit()) {
                    DialogSure.showDialog(getContext(), "确定要删除这些商品？", new DialogSure.CallBack() {
                        @Override
                        public void onSure() {
                        }
                    });
                }else {
                    DialogSure.showDialog(getContext(), "确定要下单？", new DialogSure.CallBack() {
                        @Override
                        public void onSure() {
                            OrderAddActivity.start(getContext());
                        }
                    });
                }
                break;
        }
    }
}
