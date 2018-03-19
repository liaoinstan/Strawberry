package com.magicbeans.xgate.bean.checkout;

import com.magicbeans.xgate.bean.postbean.FreeGift;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marie on 2018/3/19.
 */

public class GiftOption implements Serializable{
    private List<FreeGift> GiftItems;

    public List<FreeGift> getGiftItems() {
        return GiftItems;
    }

    public void setGiftItems(List<FreeGift> giftItems) {
        GiftItems = giftItems;
    }
}
