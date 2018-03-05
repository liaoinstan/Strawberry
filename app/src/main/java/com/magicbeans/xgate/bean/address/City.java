package com.magicbeans.xgate.bean.address;

import com.magicbeans.xgate.helper.AreaReadHelper2;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marie on 2018/3/5.
 */

public class City implements Serializable {
    private String name;
    private String district;
    private List<District> districtBean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<District> getDistrictBean() {
        if (districtBean == null) {
            districtBean = AreaReadHelper2.getInstance().readDistrictList(district);
        }
        return districtBean;
    }

    public void setDistrictBean(List<District> districtBean) {
        this.districtBean = districtBean;
    }
}
