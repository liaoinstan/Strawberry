package com.ins.common.view.bundleimgview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ins.common.R;
import com.ins.common.common.DragItemTouchCallback;
import com.ins.common.common.OnRecyclerItemClickListener;
import com.ins.common.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class BundleImgView extends FrameLayout {

    private RecyclerView recyclerView;
    private RecycleAdapterBundleImg adapter;
    private List<BundleImgEntity> results = new ArrayList<>();

    private ViewGroup root;

    private Context context;
    private LayoutInflater inflater;

    private boolean needDrag = true;

    public void setNeedDrag(boolean needDrag) {
        this.needDrag = needDrag;
    }

    public List<BundleImgEntity> getResults() {
        return results;
    }

    public BundleImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        root = (ViewGroup) inflater.inflate(R.layout.bundle, this, true);
        initBase();
        initView();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
        recyclerView = (RecyclerView) root.findViewById(R.id.recycle_bundle);
    }

    private void initCtrl() {
//        results.add(new BundleImgEntity("http://v1.qzone.cc/avatar/201503/30/13/53/5518e4e8d705e435.jpg%21200x200.jpg"));
//        results.add(new BundleImgEntity("http://img1.touxiang.cn/uploads/20131103/03-030932_368.jpg"));
//        results.add(new BundleImgEntity("http://pic.qqtn.com/up/2016-7/2016072614451372403.jpg"));
//        results.add(new BundleImgEntity("http://b.hiphotos.baidu.com/zhidao/pic/item/d1a20cf431adcbefef0f982fabaf2edda3cc9fe4.jpg"));
//        results.add(new BundleImgEntity("http://d.hiphotos.baidu.com/zhidao/pic/item/7a899e510fb30f24b12d36e7cd95d143ac4b039b.jpg"));

        adapter = new RecycleAdapterBundleImg(context, results);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(adapter);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragItemTouchCallback(adapter).setOnDragListener(null));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (needDrag) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.vibrate(context, 70);   //震动70ms
                }
            }
        });
    }

    public void freshCtrl() {
        adapter.notifyDataSetChanged();
    }

    public void setOnBundleClickListener(OnBundleClickListener bundleClickListener) {
        adapter.setBundleClickListener(bundleClickListener);
    }

    public interface OnBundleClickListener {
        void onPhotoDelClick(View v);

        void onPhotoShowClick(String path);

        void onPhotoAddClick(View v);
    }

    public void setDelEnable(boolean enable) {
        adapter.setDelEnable(enable);
    }

    public void addPhoto(String path) {
        adapter.getResults().add(new BundleImgEntity(path));
        adapter.notifyItemInserted(adapter.getResults().size());
    }

    public void clear(){
        adapter.getResults().clear();
    }

    public void addPhotos(String[] paths) {
        for (String path : paths) {
            adapter.getResults().add(new BundleImgEntity(path));
        }
        adapter.notifyDataSetChanged();
    }

    ///////////////接口
    public void setOnBundleLoadImgListener(OnBundleLoadImgListener onBundleLoadImgListener) {
        adapter.setOnBundleLoadImgListener(onBundleLoadImgListener);
    }

    public interface OnBundleLoadImgListener {
        void onloadImg(ImageView imageView, String imgurl, int defaultSrc);
    }
}
