package com.magicbeans.xgate.bean.eva;

import com.ins.common.entity.BaseSelectBean;
import com.ins.common.view.bundleimgview.BundleImgEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */

public class Eva implements Serializable{

    /**
     "CommentId": "70004652",
     "AccName": "颜 丽",
     "Seal": "False",
     "Subject": "柠檬生姜",
     "Content": "小清新的味道，没有想象中的姜味，柠檬味为主，",
     "Rating": "4.00000000",
     "YesCount": "0",
     "NoCount": "0",
     "CommentDate": "16/06/2016"
     */

    private String CommentId;
    private String AccName;
    private boolean Seal;
    private String Subject;
    private String Content;
    private String Rating;
    private String YesCount;
    private String NoCount;
    private String CommentDate;

    public Eva() {
    }

    public Eva(List<BundleImgEntity> imgs) {
        this.imgs = imgs;
    }

    //暂无图片
    private List<BundleImgEntity> imgs;

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String CommentId) {
        this.CommentId = CommentId;
    }

    public String getAccName() {
        return AccName;
    }

    public void setAccName(String AccName) {
        this.AccName = AccName;
    }

    public boolean getSeal() {
        return Seal;
    }

    public void setSeal(boolean Seal) {
        this.Seal = Seal;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String Rating) {
        this.Rating = Rating;
    }

    public String getYesCount() {
        return YesCount;
    }

    public void setYesCount(String YesCount) {
        this.YesCount = YesCount;
    }

    public String getNoCount() {
        return NoCount;
    }

    public void setNoCount(String NoCount) {
        this.NoCount = NoCount;
    }

    public String getCommentDate() {
        return CommentDate;
    }

    public void setCommentDate(String CommentDate) {
        this.CommentDate = CommentDate;
    }

    public List<BundleImgEntity> getImgs() {
        return imgs;
    }

    public void setImgs(List<BundleImgEntity> imgs) {
        this.imgs = imgs;
    }
}
