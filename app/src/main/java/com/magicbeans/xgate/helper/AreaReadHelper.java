package com.magicbeans.xgate.helper;

import android.content.Context;

import com.alibaba.fastjson.JSONReader;
import com.ins.common.utils.L;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan on 2018/1/22.
 */

public class AreaReadHelper {
    private static AreaReadHelper instance;
    private Context context;

    private AreaReadHelper(Context context) {
        this.context = context;
    }

    public static synchronized AreaReadHelper with(Context context) {
        if (instance == null) instance = new AreaReadHelper(context);
        return instance;
    }

    private String fileName = "area.json";
    /////////////////////////////////////


    public List<String> readProvinceList() {
        List<String> results = new ArrayList<>();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            JSONReader reader = new JSONReader(inputReader);
            reader.startArray();
            while (reader.hasNext()) {
                reader.startObject();
                while (reader.hasNext()) {
                    String key = reader.readString();
                    String value = reader.readString();
                    if (key.equals("name")) {
                        results.add(value);
                    }
                }
                reader.endObject();
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

    public List<String> readCityList(String province) {
        List<String> cityList = new ArrayList<>();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            JSONReader reader = new JSONReader(inputReader);
            reader.startArray();
            while (reader.hasNext()) {
                reader.startObject();
                Map<String, String> map = new HashMap<>();
                while (reader.hasNext()) {
                    String key = reader.readString();
                    String value = reader.readString();
                    map.put(key, value);
                }
                reader.endObject();
                if (map.get("name").equals(province)) {
                    String citys = map.get("citys");
                    cityList.addAll(readNames(citys));
                }
            }
            reader.endArray();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            L.e(e.getMessage());
        } finally {
            return cityList;
        }
    }

    public List<String> readDistrictList(String city) {
        List<String> districtList = new ArrayList<>();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            JSONReader reader = new JSONReader(inputReader);
            reader.startArray();
            while (reader.hasNext()) {
                reader.startObject();
                Map<String, String> mapcity = new HashMap<>();
                while (reader.hasNext()) {
                    mapcity.put(reader.readString(), reader.readString());
                }
                reader.endObject();
                JSONReader readerCity = new JSONReader(new StringReader(mapcity.get("citys")));
                readerCity.startArray();
                while (readerCity.hasNext()) {
                    readerCity.startObject();
                    Map<String, String> map = new HashMap<>();
                    while (readerCity.hasNext()) {
                        String key = readerCity.readString();
                        String value = readerCity.readString();
                        map.put(key, value);
                    }
                    readerCity.endObject();
                    if (map.get("name").equals(city)) {
                        String districts = map.get("district");
                        districtList.addAll(readNames(districts));
                    }
                }
                readerCity.endArray();
            }
            reader.endArray();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            L.e(e.getMessage());
        } finally {
            return districtList;
        }
    }

    private List<String> readNames(String json) {
        List<String> results = new ArrayList<>();
        try {
            JSONReader reader = new JSONReader(new StringReader(json));
            reader.startArray();
            while (reader.hasNext()) {
                reader.startObject();
                while (reader.hasNext()) {
                    String key = reader.readString();
                    String value = reader.readString();
                    if (key.equals("name")) {
                        results.add(value);
                    }
                }
                reader.endObject();
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
