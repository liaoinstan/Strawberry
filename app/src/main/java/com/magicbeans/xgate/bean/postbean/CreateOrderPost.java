package com.magicbeans.xgate.bean.postbean;

import com.google.gson.Gson;
import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.shopcart.ShopCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marie on 2018/1/29.
 */

public class CreateOrderPost implements Serializable {

    private Cart Cart;
    private Customer Customer;
    private Promotion Promotion;
    private Payment Payment;
    private SelectedShipment SelectedShipment;
    private String CurrId;
    private String Region;
    private boolean isGift;
    private String GiftMsg;
    private String SpecialInstruction;
    private boolean isInstruction;

    //################# 业务方法 #################

    public static List<Prod> tansProdList(List<ShopCart> goods) {
        ArrayList<Prod> prods = new ArrayList<>();
        if (!StrUtil.isEmpty(goods)) {
            for (ShopCart good : goods) {
                prods.add(new Prod(good));
            }
        }
        return prods;
    }

    //################# 业务方法 #################

    public com.magicbeans.xgate.bean.postbean.Cart getCart() {
        return Cart;
    }

    public void setCart(com.magicbeans.xgate.bean.postbean.Cart cart) {
        Cart = cart;
    }

    public com.magicbeans.xgate.bean.postbean.Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(com.magicbeans.xgate.bean.postbean.Customer customer) {
        Customer = customer;
    }

    public com.magicbeans.xgate.bean.postbean.Promotion getPromotion() {
        return Promotion;
    }

    public void setPromotion(com.magicbeans.xgate.bean.postbean.Promotion promotion) {
        Promotion = promotion;
    }

    public com.magicbeans.xgate.bean.postbean.Payment getPayment() {
        return Payment;
    }

    public void setPayment(com.magicbeans.xgate.bean.postbean.Payment payment) {
        Payment = payment;
    }

    public com.magicbeans.xgate.bean.postbean.SelectedShipment getSelectedShipment() {
        return SelectedShipment;
    }

    public void setSelectedShipment(com.magicbeans.xgate.bean.postbean.SelectedShipment selectedShipment) {
        SelectedShipment = selectedShipment;
    }

    public String getCurrId() {
        return CurrId;
    }

    public void setCurrId(String currId) {
        CurrId = currId;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public boolean isGift() {
        return isGift;
    }

    public void setGift(boolean gift) {
        isGift = gift;
    }

    public String getGiftMsg() {
        return GiftMsg;
    }

    public void setGiftMsg(String giftMsg) {
        GiftMsg = giftMsg;
    }

    public String getSpecialInstruction() {
        return SpecialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        SpecialInstruction = specialInstruction;
    }

    public boolean isInstruction() {
        return isInstruction;
    }

    public void setInstruction(boolean instruction) {
        isInstruction = instruction;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
