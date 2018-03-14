package com.magicbeans.xgate.bean.shopcart;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6.
 */

public class Promote implements Serializable{


    /**
     * DiscID : 175
     * Name : 10% Off Makeup (White Valentine's Day CN)
     * DiscAmount : -&#165;15.30
     */

    private int DiscID;
    private String Name;
    private String DiscAmount;

    public int getDiscID() {
        return DiscID;
    }

    public void setDiscID(int DiscID) {
        this.DiscID = DiscID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDiscAmount() {
        return DiscAmount;
    }

    public void setDiscAmount(String DiscAmount) {
        this.DiscAmount = DiscAmount;
    }
}
