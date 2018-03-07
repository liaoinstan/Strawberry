package com.ins.common.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan on 2018/1/22.
 */

public class JsonReadHelper {
    private JsonReadHelper() {
    }

    public static synchronized JsonReadHelper newInstance() {
        return new JsonReadHelper();
    }
    /////////////////////////////////////

    private JsonCallback callback;

    public void read(String json, JsonCallback callback) {
        this.callback = callback;
        try {
            JSONTokener jsonParser = new JSONTokener(json);
            JSONObject root = (JSONObject) jsonParser.nextValue();
            analysisJson(root);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void analysisJson(Object objJson) throws JSONException {
        //如果obj为json数组
        if (objJson instanceof JSONArray) {
            JSONArray objArray = (JSONArray) objJson;
            for (int i = 0; i < objArray.length(); i++) {
                analysisJson(objArray.get(i));
            }
        }
        //如果为json对象
        else if (objJson instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) objJson;
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = it.next().toString();
                Object object = jsonObject.get(key);
                //如果得到的是数组
                if (object instanceof JSONArray) {
                    JSONArray objArray = (JSONArray) object;
                    analysisJson(objArray);
                }
                //如果key中是一个json对象
                else if (object instanceof JSONObject) {
                    analysisJson((JSONObject) object);
                }
                //如果key中是其他
                else {
                }
                if (callback != null) callback.onRead(key, object);
            }
        }
    }
//                    System.out.println("[" + key + "]:" + object.toString() + " ");

    public interface JsonCallback {
        void onRead(String key, Object object);
    }
}
