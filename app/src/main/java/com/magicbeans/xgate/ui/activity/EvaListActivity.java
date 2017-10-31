package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.bean.eva.EvaWrap;
import com.magicbeans.xgate.databinding.ActivityEvalistBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterEva;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.List;
import java.util.Map;

public class EvaListActivity extends BaseAppCompatActivity {

    private ActivityEvalistBinding binding;
    private RecycleAdapterEva adapter;

    private String prodId;

    public static void start(Context context, String prodId) {
        Intent intent = new Intent(context, EvaListActivity.class);
        intent.putExtra("prodId", prodId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_evalist);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        prodId = getIntent().getStringExtra("prodId");
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterEva(this);
        adapter.setNeedRecomment(false);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.addItemDecoration(new ItemDecorationDivider(this));
        binding.recycler.setAdapter(adapter);
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netProductReview(0);
            }
        });
        binding.spring.setHeader(new AliHeader(this, false));
        binding.spring.setFooter(new AliFooter(this, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    private void initData() {
        netProductReview(0);
    }

    ///////////////////////////////////
    //////////////分页查询
    ///////////////////////////////////

    private int page;
    private final int PAGE_COUNT = 10;

    /**
     * type:0 首次加载 1:下拉刷新 2:上拉加载
     * @param type
     */
    private void netProductReview(final int type) {
        Map<String, Object> param = new NetParam()
                .put("page", type == 0 || type == 1 ? "1" : page + 1 + "")
                .put("prodId", prodId)
                .build();
        if (type == 0) binding.loadingview.showLoadingView();
        NetApi.NI().netProductReview(NetParam.newInstance().put(param).build()).enqueue(new STCallback<EvaWrap>(EvaWrap.class) {
            @Override
            public void onSuccess(int status, EvaWrap bean, String msg) {
                List<Eva> evas = bean.getProducts();
                if (!StrUtil.isEmpty(evas)) {
                    //下拉加载和首次加载要清除原有数据并把页码置为1，上拉加载不断累加页码
                    if (type == 0 || type == 1) {
                        adapter.getResults().clear();
                        page = 1;
                    } else {
                        page++;
                    }
                    adapter.getResults().addAll(evas);
                    adapter.notifyDataSetChanged();

                    //加载结束恢复列表
                    if (type == 0) {
                        binding.loadingview.showOut();
                    } else {
                        binding.spring.onFinishFreshAndLoad();
                    }
                } else {
                    //没有数据设置空数据页面，下拉加载不用，仅提示
                    if (type == 0 || type == 1) {
                        binding.loadingview.showLackView();
                    } else {
                        binding.spring.onFinishFreshAndLoad();
                        ToastUtil.showToastShort("没有更多的数据了");
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                //首次加载发生异常设置error页面，其余仅提示
                if (type == 0) {
                    binding.loadingview.showFailView();
                } else {
                    binding.spring.onFinishFreshAndLoad();
                }
            }
        });
    }
}
