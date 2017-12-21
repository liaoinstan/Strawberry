package com.magicbeans.xgate.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.data.DataRepository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class EvaListViewModel extends ViewModel {

    //入口参数
    private String prodId;
    //数据结果集
    private MutableLiveData<List<Eva>> results;
    //0:无 1：加载中状态 2：空数据状态 3：加载失败状态
    public MutableLiveData<LoadingStatus> loadingViewStatus = new MutableLiveData<>();
    public MutableLiveData<Boolean> finishSpringLoad = new MutableLiveData<>();
    //数据源
    private DataRepository dataRepository;

    private int page = 1;

    public enum LoadingStatus {
        NONE, LOADING, EMPTY, ERROR
    }

    public EvaListViewModel(String prodId) {
        this.prodId = prodId;
        this.dataRepository = DataRepository.getInstance();
    }

    //首次填充数据
    public LiveData<List<Eva>> resetResults() {
        results = new MutableLiveData();
        dataRepository.queryResults(prodId, 1, new DataRepository.DataCallback() {
            @Override
            public void onSuccess(int status, List<Eva> evas, String msg) {
                results.setValue(evas);
                loadingViewStatus.setValue(LoadingStatus.NONE);
            }

            @Override
            public void onEmpty(int status, String msg) {
                loadingViewStatus.setValue(LoadingStatus.EMPTY);
            }

            @Override
            public void onError(int status, String msg) {
                loadingViewStatus.setValue(LoadingStatus.ERROR);
            }

            @Override
            public void onStart() {
                loadingViewStatus.setValue(LoadingStatus.LOADING);
            }
        });
        return results;
    }

    //刷新或加载更多
    public LiveData<List<Eva>> refreshResults(final boolean loadmore) {
        dataRepository.queryResults(prodId, loadmore ? page + 1 : 1, new DataRepository.DataCallback() {
            @Override
            public void onSuccess(int status, List<Eva> evas, String msg) {
                List<Eva> allevas = results.getValue();
                if (!loadmore) allevas.clear();
                else page++;
                allevas.addAll(evas);
                results.setValue(allevas);
                finishSpringLoad.setValue(true);
            }

            @Override
            public void onEmpty(int status, String msg) {
                finishSpringLoad.setValue(true);
            }

            @Override
            public void onError(int status, String msg) {
                finishSpringLoad.setValue(true);
            }

            @Override
            public void onStart() {
                finishSpringLoad.setValue(false);
            }
        });
        return results;
    }

    public LiveData<List<Eva>> getResults() {
        return results;
    }
}
