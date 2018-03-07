package com.magicbeans.xgate.bean.checkout;

import android.text.TextUtils;

import com.ins.common.utils.StrUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class CheckoutContent implements Serializable {
    private List<Notice> OrderSummaryNotices;
    private List<Notice> VATNotices;
    private List<Notice> ShippingNotics;
    private List<Notice> ImportantNotices;
    private List<Notice> AddressNotices;

    //##################### 业务方法 #######################

    public String getNoticeStr() {
        String noticeOrderSum = getNoticeByOne(OrderSummaryNotices);
        String noticeVAT = getNoticeByOne(VATNotices);
        String noticeShipping = getNoticeByOne(ShippingNotics);
        String noticeImportant = getNoticeByOne(ImportantNotices);
        String noticeAddress = getNoticeByOne(AddressNotices);

        String noticeAll = "";
        if (!TextUtils.isEmpty(noticeOrderSum)) {
            noticeAll += noticeOrderSum + "\n";
        }
        if (!TextUtils.isEmpty(noticeVAT)) {
            noticeAll += noticeVAT + "\n";
        }
        if (!TextUtils.isEmpty(noticeShipping)) {
            noticeAll += noticeShipping + "\n";
        }
        if (!TextUtils.isEmpty(noticeImportant)) {
            noticeAll += noticeImportant + "\n";
        }
        if (!TextUtils.isEmpty(noticeAddress)) {
            noticeAll += noticeAddress + "\n";
        }
        return StrUtil.subLastChart(noticeAll, "\n");
    }

    public String getNoticeByOne(List<Notice> noticeList) {
        if (StrUtil.isEmpty(noticeList)) return "";
        String noticeStrAll = "";
        for (Notice notice : noticeList) {
            String noticeStr = notice.getNoticeStr();
            if (!TextUtils.isEmpty(noticeStr)) {
                noticeStrAll += notice.getNoticeStr() + "\n";
            }
        }
        return StrUtil.subLastChart(noticeStrAll, "\n");
    }

    //##################### 业务方法 #######################

    public List<Notice> getOrderSummaryNotices() {
        return OrderSummaryNotices;
    }

    public void setOrderSummaryNotices(List<Notice> orderSummaryNotices) {
        OrderSummaryNotices = orderSummaryNotices;
    }

    public List<Notice> getVATNotices() {
        return VATNotices;
    }

    public void setVATNotices(List<Notice> VATNotices) {
        this.VATNotices = VATNotices;
    }

    public List<Notice> getShippingNotics() {
        return ShippingNotics;
    }

    public void setShippingNotics(List<Notice> shippingNotics) {
        ShippingNotics = shippingNotics;
    }

    public List<Notice> getImportantNotices() {
        return ImportantNotices;
    }

    public void setImportantNotices(List<Notice> importantNotices) {
        ImportantNotices = importantNotices;
    }

    public List<Notice> getAddressNotices() {
        return AddressNotices;
    }

    public void setAddressNotices(List<Notice> addressNotices) {
        AddressNotices = addressNotices;
    }
}
