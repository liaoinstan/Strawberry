package com.magicbeans.xgate.bean.address;

import com.magicbeans.xgate.helper.AreaReadHelper2;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marie on 2018/3/5.
 */

public class Province implements Serializable {
    private String name;
    private String citys;
    private List<City> citysBean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys;
    }

    public List<City> getCitysBean() {
        if (citysBean == null) {
            citysBean = AreaReadHelper2.getInstance().readCityList(citys);
        }
        return citysBean;
    }

    public void setCitysBean(List<City> citysBean) {
        this.citysBean = citysBean;
    }
}
