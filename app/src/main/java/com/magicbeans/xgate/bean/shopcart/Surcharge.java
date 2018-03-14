package com.magicbeans.xgate.bean.shopcart;

import android.text.Html;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6.
 */

public class Surcharge implements Serializable {


    /**
     * ID : 0
     * Name : 护发产品附加费 <a href='/layerShippingNotice.aspx?type=3' data-fancybox-type='iframe' class='lightbox' >[?]</a>
     * Amount : 65.93
     * txtAmount : &#165;65.93
     * Type : 3
     */

    private int ID;
    private String Name;
    private double Amount;
    private String txtAmount;
    private int Type;

    //////////////////////////////////////////////

    public String getNameNoHtml() {
        return Html.fromHtml(Name).toString();
    }

    //////////////////////////////////////////////

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

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
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
