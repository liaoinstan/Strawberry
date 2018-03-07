package com.magicbeans.xgate.bean.shopcart;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6.
 */

public class Shipment implements Serializable{


    /**
     * ID : 1
     * Name : 标准付运
     * Amount : 0
     * txtAmount : &#165;0.00
     * Type : 1
     */

    private int ID;
    private String Name;
    private float Amount;
    private String txtAmount;
    private int Type;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float Amount) {
        this.Amount = Amount;
    }

    public String getTxtAmount() {
        return txtAmount;
    }

    public void setTxtAmount(String txtAmount) {
        this.txtAmount = txtAmount;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }
}
