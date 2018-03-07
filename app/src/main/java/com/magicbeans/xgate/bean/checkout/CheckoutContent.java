package com.magicbeans.xgate.bean.checkout;

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
