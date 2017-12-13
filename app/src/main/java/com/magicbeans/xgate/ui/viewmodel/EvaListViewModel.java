package com.magicbeans.xgate.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

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
    //数据源
    private DataRepository dataRepository;

    public enum LoadingStatus {
        NONE, LOADING, EMPTY, ERROR
    }

    public EvaListViewModel(String prodId) {
        this.prodId = prodId;
        this.dataRepository = DataRepository.getInstance();
    }

    public LiveData<List<Eva>> resetResults() {
        dataRepository.queryResults(prodId, 0, 20, new DataRepository.DataCallback() {
            @Override
            public void onSuccess(int status, List<Eva> evas, String msg) {
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
                loadingViewStatus.setValue(LoadingStatus.NONE);
            }
        });
        return results;
    }

//    public LiveData<List<Eva>> queryResults() {
//        results = dataRepository.queryResults(prodId, 0, 20);
//        dataRepository.isEmpty.observeForever(new Observer<Boolean>() {
//            @Override
//            public void onChanged(@Nullable Boolean aBoolean) {
//
//            }
//        });
//        return results;
//    }
//
//    public LiveData<List<Eva>> queryMoreResults() {
//
//    }

    public LiveData<List<Eva>> getResults() {
        return results;
    }
}
