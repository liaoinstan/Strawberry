package com.magicbeans.xgate.helper;

import android.content.Context;

import com.alibaba.fastjson.JSONReader;
import com.ins.common.utils.L;
import com.magicbeans.xgate.bean.address.City;
import com.magicbeans.xgate.bean.address.District;
import com.magicbeans.xgate.bean.address.Province;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan on 2018/1/22.
 */

public class AreaReadHelper2 {
    private static AreaReadHelper2 instance;

    private AreaReadHelper2() {
    }

    public static synchronized AreaReadHelper2 getInstance() {
        if (instance == null) instance = new AreaReadHelper2();
        return instance;
    }

    private String fileName = "area.json";
    /////////////////////////////////////


    public List<Province> readProvinceList(Context context) {
        List<Province> results = new ArrayList<>();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            JSONReader reader = new JSONReader(inputReader);
            reader.startArray();
            while (reader.hasNext()) {
                Province province = reader.readObject(Province.class);
                results.add(province);
            }
            reader.endArray();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            L.e(e.getMessage());
        } finally {
            return results;
        }
    }

    public List<City> readCityList(String json) {
        List<City> results = new ArrayList<>();
        try {
            JSONReader reader = new JSONReader(new StringReader(json));
            reader.startArray();
            while (reader.hasNext()) {
                City city = reader.readObject(City.class);
                results.add(city);
            }
            reader.endArray();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            L.e(e.getMessage());
        } finally {
            return results;
        }
    }

    public List<District> readDistrictList(String json) {
        List<District> results = new ArrayList<>();
        try {
            JSONReader reader = new JSONReader(new StringReader(json));
            reader.startArray();
            while (reader.hasNext()) {
                District district = reader.readObject(District.class);
                results.add(district);
            }
            reader.endArray();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            L.e(e.getMessage());
        } finally {
            return results;
        }
    }

}
