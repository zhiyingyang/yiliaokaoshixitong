package com.jsmosce.Tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jsmosce.data.DataHospital;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by lx on 2017/5/16.
 */

public class Tools {
    public static void myMakeText(Context context, String message) {
        //  if (BaseApplication.caseId == 0) return;
        if ("考案Id为空或格式有误".equals(message) || "输入考案Id有误".equals(message) || "考案Id有误".equals(message)) return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.d(TAG, message);
    }

    /**
     * 对象转json
     */
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * json转对象
     */
    public static <T> T toObject(String jsonString, Class<T> cls) {
        T t = null;
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        try {
            t = new Gson().fromJson(jsonObject.toString(), cls);
        } catch (Exception e) {
        }
        return t;
    }


    /**
     * @param flag 发送通知
     */
    public static void sendEvent(int flag) {

        FavorEvent anyEventType = new FavorEvent();
        anyEventType.setId(flag);
        EventBus.getDefault().postSticky(anyEventType);

    }

    public static String[] listToArray(List<DataHospital.HospitalInfoBean> list) {
        String[] strings = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strings[i] = list.get(i).getName();
        }
        return strings;
    }

    public static boolean isNull(String str, String message, Context context) {
        if (str == null || "".equals(str)) {
            Tools.myMakeText(context, message);
            return false;
        }
        return true;
    }
}
