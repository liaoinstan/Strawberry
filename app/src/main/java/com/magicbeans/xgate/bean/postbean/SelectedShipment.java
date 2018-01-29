package com.magicbeans.xgate.bean.postbean;

import java.io.Serializable;

/**
 * Created by Marie on 2018/1/29.
 */

public class SelectedShipment implements Serializable {
    private int SubShipmentType;
    private int ID;

    public SelectedShipment() {
    }

    public SelectedShipment(int subShipmentType, int ID) {
        SubShipmentType = subShipmentType;
        this.ID = ID;
    }

    public int getSubShipmentType() {
        return SubShipmentType;
    }

    public void setSubShipmentType(int subShipmentType) {
        SubShipmentType = subShipmentType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
