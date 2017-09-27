package com.ins.common.view.bundleimgview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    //一行多少个item数量
    private int colcount = 4;
    private boolean dragble;
    private boolean editble;

    public void setDragble(boolean dragble) {
        this.dragble = dragble;
    }

    public List<BundleImgEntity> getResults() {
        return results;
    }

    public BundleImgView(@NonNull Context context) {
        this(context, null);
    }

    public BundleImgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BundleImgView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        // 初始化各项组件
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BundleImgView);
        editble = a.getBoolean(R.styleable.BundleImgView_editble, true);
        dragble = a.getBoolean(R.styleable.BundleImgView_dragble, false);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.bundle, this, true);
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
        //测试数据
//        results.add(new BundleImgEntity("http://v1.qzone.cc/avatar/201503/30/13/53/5518e4e8d705e435.jpg%21200x200.jpg"));
//        results.add(new BundleImgEntity("http://img1.touxiang.cn/uploads/20131103/03-030932_368.jpg"));
//        results.add(new BundleImgEntity("http://pic.qqtn.com/up/2016-7/2016072614451372403.jpg"));
//        results.add(new BundleImgEntity("http://b.hiphotos.baidu.com/zhidao/pic/item/d1a20cf431adcbefef0f982fabaf2edda3cc9fe4.jpg"));
//        results.add(new BundleImgEntity("http://d.hiphotos.baidu.com/zhidao/pic/item/7a899e510fb30f24b12d36e7cd95d143ac4b039b.jpg"));

        adapter = new RecycleAdapterBundleImg(context, results);
        adapter.setEditble(editble);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(context, colcount));
        recyclerView.setAdapter(adapter);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DragItemTouchCallback(adapter).setOnDragListener(null));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (dragble) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.vibrate(context, 70);   //震动70ms
                }
            }
        });
    }

    //############### 对外方法 ################

    public void notifyDataSetChanged() {
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

    public void setEditble(boolean editble) {
        this.editble = editble;
        adapter.setEditble(editble);
    }

    public void addPhoto(BundleImgEntity bundle) {
        adapter.getResults().add(bundle);
        adapter.notifyItemInserted(adapter.getResults().size());
    }

    public void clear() {
        adapter.getResults().clear();
    }

    public void setPhotos(List<BundleImgEntity> results) {
        adapter.getResults().clear();
        adapter.getResults().addAll(results);
        adapter.notifyDataSetChanged();
    }

    public void setOnBundleLoadImgListener(OnBundleLoadImgListener onBundleLoadImgListener) {
        adapter.setOnBundleLoadImgListener(onBundleLoadImgListener);
    }

    public interface OnBundleLoadImgListener {
        void onloadImg(ImageView imageView, String imgurl, int defaultSrc);
    }
}
